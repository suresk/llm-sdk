package net.uresk.ai.llm.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Choice
{

    @JsonProperty
    private int index;

    @JsonProperty
    private FinishReason finishReason;

    @JsonProperty
    private ResponseMessage message;

    public Choice(){}

    public Choice(int index, FinishReason finishReason, ResponseMessage message)
    {
        this.index = index;
        this.finishReason = finishReason;
        this.message = message;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public FinishReason getFinishReason()
    {
        return finishReason;
    }

    public void setFinishReason(FinishReason finishReason)
    {
        this.finishReason = finishReason;
    }

    public ResponseMessage getMessage()
    {
        return message;
    }

    public void setMessage(ResponseMessage message)
    {
        this.message = message;
    }
}
