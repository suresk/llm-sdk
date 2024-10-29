package net.uresk.ai.llm.content;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Content
{

    @JsonProperty
    private String type;

    public Content(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
