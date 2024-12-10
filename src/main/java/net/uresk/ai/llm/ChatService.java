package net.uresk.ai.llm;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.uresk.ai.llm.exceptions.ApiException;
import net.uresk.ai.llm.exceptions.ApiKeyException;
import net.uresk.ai.llm.exceptions.GeographicException;
import net.uresk.ai.llm.exceptions.InvalidAuthenticationException;
import net.uresk.ai.llm.exceptions.OpenAIServiceError;
import net.uresk.ai.llm.exceptions.QuotaException;
import net.uresk.ai.llm.exceptions.RateLimitExceededException;
import net.uresk.ai.llm.exceptions.ServerOverloadedException;
import net.uresk.ai.llm.request.ChatRequest;
import net.uresk.ai.llm.request.ResponseFormat;
import net.uresk.ai.llm.response.ChatResponse;
import net.uresk.ai.llm.response.Metadata;
import net.uresk.ai.llm.response.RateLimitInfo;
import net.uresk.ai.llm.utils.MapperUtil;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatService
{

    private final String apiKey;

    private final HttpClient httpClient;

    private final ObjectMapper objectMapper;

    public ChatService(String apiKey, HttpClient httpClient, ObjectMapper objectMapper)
    {
        this.apiKey = apiKey;
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public ChatResponse sendRequest(ChatRequest request)
    {
        try
        {
            var httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(request)))
                    .build();

            var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (httpResponse.statusCode() < 300)
            {
                switch (request.getResponseFormat().getType())
                {
                    case JSON_OBJECT:
                    case JSON_SCHEMA:
                        return objectMapper.readValue(httpResponse.body(), ChatResponse.class);
                }
                return objectMapper.readValue(httpResponse.body(), ChatResponse.class);
            }

            Metadata metadata = getMetadata(httpResponse);

            switch (httpResponse.statusCode())
            {
                case 401:
                    handle401(httpResponse, metadata);
                case 403:
                    throw new GeographicException(metadata);
                case 429:
                    handle429(httpResponse, metadata);
                case 500:
                    throw new OpenAIServiceError(metadata);
                case 503:
                    throw new ServerOverloadedException(metadata);
                default:
                    throw new ApiException("Unknown error: " + httpResponse.body(), metadata);
            }
        }
        catch (IOException | InterruptedException e)
        {
            throw new RuntimeException("Error sending request", e);
        }
    }

    private void handle401(HttpResponse<String> response, Metadata metadata)
    {
        String body = response.body();

        if (body.contains("You didn't provide an API key"))
        {
            throw new InvalidAuthenticationException(metadata);
        }
        else if (body.contains("invalid_api_key"))
        {
            throw new ApiKeyException(metadata);
        }
        else
        {
            throw new ApiException("Unknown 401 error: " + body, metadata);
        }
    }

    private String nullSafeStringHeader(HttpResponse<String> response, String header)
    {
        return response.headers().firstValue(header).orElse(null);
    }

    private Long nullSafeLongHeader(HttpResponse<String> response, String header)
    {
        String value = nullSafeStringHeader(response, header);
        return value == null ? null : Long.parseLong(value);
    }

    private Long durationToMsHandler(HttpResponse<String> response, String header)
    {
        String value = nullSafeStringHeader(response, header);
        return value == null ? null : parseDuration(value).toMillis();
    }

    private static Duration parseDuration(String text) {
        // Pattern to capture the numeric value and unit (e.g., "50ms", "3s", "2m")
        Pattern pattern = Pattern.compile("(\\d+)([a-zA-Z]+)");
        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()) {
            long amount = Long.parseLong(matcher.group(1));
            String unit = matcher.group(2).toLowerCase();

            // Determine the unit and return the appropriate Duration
            switch (unit) {
                case "ms":
                    return Duration.ofMillis(amount);
                case "s":
                    return Duration.ofSeconds(amount);
                case "m":
                    return Duration.ofMinutes(amount);
                case "h":
                    return Duration.ofHours(amount);
                case "d":
                    return Duration.ofDays(amount);
                default:
                    throw new IllegalArgumentException("Unknown time unit: " + unit);
            }
        } else {
            throw new IllegalArgumentException("Invalid duration format: " + text);
        }
    }

    private Metadata getMetadata(HttpResponse<String> response)
    {
        RateLimitInfo rateLimitInfo = new RateLimitInfo(
                nullSafeLongHeader(response, "x-ratelimit-limit-requests"),
                nullSafeLongHeader(response, "x-ratelimit-limit-tokens"),
                nullSafeLongHeader(response, "x-ratelimit-remaining-tokens"),
                nullSafeLongHeader(response, "x-ratelimit-remaining-requests"),
                durationToMsHandler(response, "x-ratelimit-reset-tokens"),
                durationToMsHandler(response, "x-ratelimit-reset-requests")
        );

        return new Metadata(
                nullSafeStringHeader(response, "openai-organization"),
                nullSafeLongHeader(response, "openai-processing-time"),
                nullSafeStringHeader(response, "openai-version"),
                nullSafeStringHeader(response, "x-request-id"),
                rateLimitInfo
        );
    }

    private void handle429(HttpResponse<String> response, Metadata metadata)
    {
        String body = response.body();

        if (body.contains("Rate limit reached for requests"))
        {
            throw new RateLimitExceededException(metadata);
        }
        else if(body.contains("You exceeded your current quota"))
        {
            throw new QuotaException(metadata);
        }
        else
        {
            throw new ApiException("Unknown 429 error: " + body, metadata);
        }
    }

    public static Builder newBuilder()
    {
        return new Builder();
    }

    public static class Builder
    {

            private String apiKey;

            private HttpClient httpClient;

            private ObjectMapper objectMapper;

            public Builder()
            {

            }

            public Builder withApiKey(String apiKey)
            {
                this.apiKey = apiKey;
                return this;
            }

            public Builder withHttpClient(HttpClient httpClient)
            {
                this.httpClient = httpClient;
                return this;
            }

            public Builder withObjectMapper(ObjectMapper objectMapper)
            {
                this.objectMapper = objectMapper;
                return this;
            }

            public ChatService build()
            {
                if (apiKey == null)
                {
                    throw new IllegalArgumentException("API key is required");
                }

                if (httpClient == null)
                {
                    httpClient = HttpClient.newHttpClient();
                }

                if (objectMapper == null)
                {
                    objectMapper = MapperUtil.getJsonMapper();
                }

                return new ChatService(apiKey, httpClient, objectMapper);
            }
    }

}
