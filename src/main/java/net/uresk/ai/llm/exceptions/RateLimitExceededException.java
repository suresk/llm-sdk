package net.uresk.ai.llm.exceptions;

import net.uresk.ai.llm.response.Metadata;

public class RateLimitExceededException extends ApiException
{

    public RateLimitExceededException(Metadata metadata)
    {
        super("Rate limit reached. See: https://platform.openai.com/docs/guides/rate-limits", metadata);
    }

}
