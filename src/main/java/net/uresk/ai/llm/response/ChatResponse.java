package net.uresk.ai.llm.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatResponse
{

    @JsonProperty
    private String id;

    @JsonProperty
    private String object;

    @JsonProperty
    private long timestamp;

    @JsonProperty
    private String model;

    @JsonProperty
    private List<Choice> choices;

    @JsonProperty
    private Usage usage;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getObject()
    {
        return object;
    }

    public void setObject(String object)
    {
        this.object = object;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public List<Choice> getChoices()
    {
        return choices;
    }

    public void setChoices(List<Choice> choices)
    {
        this.choices = choices;
    }

    public Usage getUsage()
    {
        return usage;
    }

    public void setUsage(Usage usage)
    {
        this.usage = usage;
    }
}
