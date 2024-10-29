package net.uresk.ai.llm.exceptions;

public class RateLimitExceededException extends RuntimeException
{

    public RateLimitExceededException()
    {
        super("Rate limit reached. See: https://platform.openai.com/docs/guides/rate-limits");
    }

}
