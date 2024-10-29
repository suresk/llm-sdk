package net.uresk.ai.llm.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseFormatObject
{

    @JsonProperty
    private ResponseFormat type;

    public ResponseFormatObject(ResponseFormat type)
    {
        this.type = type;
    }

    public ResponseFormat getType()
    {
        return type;
    }

    public void setType(ResponseFormat type)
    {
        this.type = type;
    }
}
