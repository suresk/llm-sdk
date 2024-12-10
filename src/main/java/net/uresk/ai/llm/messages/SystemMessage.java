package net.uresk.ai.llm.messages;

import net.uresk.ai.llm.content.Content;
import net.uresk.ai.llm.content.TextContent;

import java.util.List;

public class SystemMessage extends Message
{

    public SystemMessage(List<Content> content)
    {
        super("system", content);
    }

    public SystemMessage(Content content)
    {
        this(List.of(content));
    }

    public SystemMessage(String text)
    {
        this(new TextContent(text));
    }

}
