package net.uresk.ai.llm;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.uresk.ai.llm.request.ChatRequest;
import net.uresk.ai.llm.response.ChatResponse;
import net.uresk.ai.llm.utils.MapperUtil;

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

            if (httpResponse.statusCode() > 201)
            {
                throw new RuntimeException("Got error response from OpenAI: " + httpResponse.statusCode() + " " + httpResponse.body());
            }

            return objectMapper.readValue(httpResponse.body(), ChatResponse.class);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error sending request", e);
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
