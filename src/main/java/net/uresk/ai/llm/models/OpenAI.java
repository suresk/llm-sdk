package net.uresk.ai.llm.models;

public enum OpenAI
{

    GPT4o("gpt-4o", 128_000, 16_384),
    O1_PREVIEW("o1-preview", 128_000, 32_768);

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

    public static OpenAI fromString(String text) {
        for (OpenAI model : OpenAI.values()) {
            if (model.key.equalsIgnoreCase(text)) {
                return model;
            }
        }
        throw new IllegalArgumentException("No constant with value " + text + " found");
    }

}
