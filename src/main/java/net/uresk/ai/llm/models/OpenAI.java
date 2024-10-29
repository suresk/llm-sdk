package net.uresk.ai.llm.models;

public enum OpenAI
{

    GPT40("gpt-4o", 128_000, 16_384);

    private String key;

    private int maxTokens;

    private int maxOutputTokens;

    OpenAI(String key, int maxTokens, int maxOutputTokens)
    {
        this.key = key;
        this.maxTokens = maxTokens;
        this.maxOutputTokens = maxOutputTokens;
    }

    public String getKey()
    {
        return key;
    }

    public int getMaxTokens()
    {
        return maxTokens;
    }

    public int getMaxOutputTokens()
    {
        return maxOutputTokens;
    }
}
