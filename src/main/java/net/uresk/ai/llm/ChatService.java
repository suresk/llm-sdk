package net.uresk.ai.llm;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.uresk.ai.llm.exceptions.ApiKeyException;
import net.uresk.ai.llm.exceptions.GeographicException;
import net.uresk.ai.llm.exceptions.InvalidAuthenticationException;
import net.uresk.ai.llm.exceptions.OpenAIServiceError;
import net.uresk.ai.llm.exceptions.QuotaException;
import net.uresk.ai.llm.exceptions.RateLimitExceededException;
import net.uresk.ai.llm.exceptions.ServerOverloadedException;
import net.uresk.ai.llm.request.ChatRequest;
import net.uresk.ai.llm.response.ChatResponse;
import net.uresk.ai.llm.utils.MapperUtil;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
                return objectMapper.readValue(httpResponse.body(), ChatResponse.class);
            }

            switch (httpResponse.statusCode())
            {
                case 401:
                    handle401(httpResponse);
                case 403:
                    throw new GeographicException();
                case 429:
                    throw new IllegalArgumentException("Rate limit exceeded");
                case 500:
                    throw new OpenAIServiceError();
                case 503:
                    throw new ServerOverloadedException();
                default:
                    throw new RuntimeException("Unknown error: " + httpResponse.body());
            }
        }
        catch (IOException | InterruptedException e)
        {
            throw new RuntimeException("Error sending request", e);
        }
    }

    private void handle401(HttpResponse<String> response)
    {
        String body = response.body();

        if (body.contains("You didn't provide an API key"))
        {
            throw new InvalidAuthenticationException();
        }
        else if (body.contains("invalid_api_key"))
        {
            throw new ApiKeyException();
        }
        else
        {
            throw new RuntimeException("Unknown 401 error: " + body);
        }
    }

    private void handle429(HttpResponse<String> response)
    {
        String body = response.body();

        if (body.contains("Rate limit reached for requests"))
        {
            throw new RateLimitExceededException();
        }
        else if(body.contains("You exceeded your current quota"))
        {
            throw new QuotaException();
        }
        else
        {
            throw new RuntimeException("Unknown 429 error: " + body);
        }
    }

    public static Builder newBuilder()
    {
        return new Builder();
    }

    public static class Builder
    {

            private String apiKey;

            private HttpClient httpClient = HttpClient.newHttpClient();

            private ObjectMapper objectMapper = MapperUtil.getJsonMapper();

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

                return new ChatService(apiKey, httpClient, objectMapper);
            }
    }

}
