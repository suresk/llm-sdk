package net.uresk.ai.llm.messages;

import net.uresk.ai.llm.content.Content;

import java.util.List;

public class AssistantMessage extends Message
{

    public AssistantMessage(List<Content> content)
    {
        super("assistant", content);
    }

}
