package net.uresk.ai.llm.content;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TextContent extends Content
{

    @JsonProperty
    private String text;

    public TextContent(String text)
    {
        super("text");
        this.text = text;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

}
