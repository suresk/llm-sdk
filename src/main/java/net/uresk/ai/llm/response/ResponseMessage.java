package net.uresk.ai.llm.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseMessage
{

    @JsonProperty
    private String role;

    @JsonProperty
    private String content;

    @JsonProperty
    private String refusal;

    public ResponseMessage() {}

    public ResponseMessage(String role, String content, String refusal)
    {
        this.role = role;
        this.content = content;
        this.refusal = refusal;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getRefusal()
    {
        return refusal;
    }

    public void setRefusal(String refusal)
    {
        this.refusal = refusal;
    }
}
