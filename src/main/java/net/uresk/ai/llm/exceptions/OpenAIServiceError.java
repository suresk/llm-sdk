package net.uresk.ai.llm.exceptions;

import net.uresk.ai.llm.response.Metadata;

public class OpenAIServiceError extends ApiException
{

    public OpenAIServiceError(Metadata metadata)
    {
        super("Server error. See status page: https://status.openai.com/", metadata);
    }

}
