package net.uresk.ai.llm.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.uresk.ai.llm.content.Content;
import net.uresk.ai.llm.content.ImageContent;
import net.uresk.ai.llm.content.TextContent;

import java.util.List;

public class UserMessage extends Message
{

    @JsonProperty
    private String name;

    public UserMessage(List<Content> content, String name)
    {
        super("user", content);
        this.name = name;
    }

    public UserMessage(List<Content> content)
    {
        this(content, null);
    }

    public UserMessage(Content content)
    {
        this(List.of(content), null);
    }

    public UserMessage(String content)
    {
        this(new TextContent(content));
    }

    public UserMessage(ImageContent content)
    {
        this(List.of(content));
    }

    public String getName()
    {
        return name;
    }
}
