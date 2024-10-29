package net.uresk.ai.llm.exceptions;

import net.uresk.ai.llm.response.Metadata;

public class ApiKeyException extends ApiException
{

    public ApiKeyException(Metadata metadata)
    {
        super("Incorrect API key - check API key string", metadata);
    }

}
