package net.uresk.ai.llm.exceptions;

public class QuotaException extends RuntimeException
{

    public QuotaException()
    {
        super("Quota exceeded - see https://platform.openai.com/account/limits");
    }

}
