package net.uresk.ai.llm.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.uresk.ai.llm.content.Content;

import java.util.List;

public class Message
{

    @JsonProperty
    private String role;

    @JsonProperty
    private List<Content> content;

    public Message(String role, List<Content> content)
    {
        this.role = role;
        this.content = content;
    }

    public String getRole()
    {
        return role;
    }

    public List<Content> getContent()
    {
        return content;
    }
}
