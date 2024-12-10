package net.uresk.ai.llm.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.uresk.ai.llm.messages.Message;
import net.uresk.ai.llm.messages.UserMessage;
import net.uresk.ai.llm.models.OpenAI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatRequest
{

    @JsonProperty
    private String model;

    @JsonProperty
    private List<Message> messages;

    @JsonProperty
    private Boolean store;

    @JsonProperty
    private Map<String, Object> metadata;

    @JsonProperty
    private Float frequencyPenalty;

    @JsonProperty
    private Integer maxCompletionTokens;

    @JsonProperty
    private Integer choices;

    @JsonProperty
    private ResponseFormatObject responseFormat;

    public ChatRequest(String model, List<Message> messages, Boolean store, Map<String, Object> metadata, Float frequencyPenalty, Integer maxCompletionTokens, Integer choices, ResponseFormatObject responseFormat)
    {
        this.model = model;
        this.messages = messages;
        this.store = store;
        this.metadata = metadata;
        this.frequencyPenalty = frequencyPenalty;
        this.maxCompletionTokens = maxCompletionTokens;
        this.choices = choices;
        this.responseFormat = responseFormat;
    }

    public String getModel()
    {
        return model;
    }

    public List<Message> getMessages()
    {
        return messages;
    }

    public Boolean getStore()
    {
        return store;
    }

    public Map<String, Object> getMetadata()
    {
        return metadata;
    }

    public Float getFrequencyPenalty()
    {
        return frequencyPenalty;
    }

    public Integer getMaxCompletionTokens()
    {
        return maxCompletionTokens;
    }

    public Integer getChoices()
    {
        return choices;
    }

    public ResponseFormatObject getResponseFormat()
    {
        return responseFormat;
    }

    public static Builder newBuilder()
    {
        return new Builder();
    }

    // Builder

    public static class Builder
    {
        private String model;
        private List<Message> messages = new ArrayList<>();
        private Boolean store;
        private Map<String, Object> metadata;
        private Float frequencyPenalty;
        private Integer maxCompletionTokens;
        private Integer choices;
        private ResponseFormatObject responseFormat;

        public Builder model(OpenAI model)
        {
            this.model = model.getKey();
            return this;
        }

        public Builder model(String model)
        {
            this.model = model;
            return this;
        }

        public Builder addMessage(Message message)
        {
            this.messages.add(message);
            return this;
        }

        public Builder addMessages(List<Message> messages)
        {
            this.messages.addAll(messages);
            return this;
        }

        public Builder store(boolean store)
        {
            this.store = store;
            return this;
        }

        public Builder metadata(Map<String, Object> metadata)
        {
            this.metadata = metadata;
            return this;
        }

        public Builder frequencyPenalty(float frequencyPenalty)
        {
            this.frequencyPenalty = frequencyPenalty;
            return this;
        }

        public Builder maxCompletionTokens(int maxCompletionTokens)
        {
            this.maxCompletionTokens = maxCompletionTokens;
            return this;
        }

        public Builder choices(int choices)
        {
            this.choices = choices;
            return this;
        }

        public Builder responseFormat(ResponseFormatObject responseFormat)
        {
            this.responseFormat = responseFormat;
            return this;
        }

        public Builder asJsonObject()
        {
            this.responseFormat = new ResponseFormatObject(ResponseFormat.JSON_OBJECT);
            return this;
        }

        public Builder withJsonSchema(Object schema)
        {
            this.responseFormat = new JsonSchemaResponseFormatObject(schema);
            return this;
        }

        public ChatRequest build()
        {
            return new ChatRequest(model, messages, store, metadata, frequencyPenalty, maxCompletionTokens, choices, responseFormat);
        }

    }
}
