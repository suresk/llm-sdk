package net.uresk.ai.llm.exceptions;

import net.uresk.ai.llm.response.Metadata;

public class QuotaException extends ApiException
{

    public QuotaException(Metadata metadata)
    {
        super("Quota exceeded - see https://platform.openai.com/account/limits", metadata);
    }

}
