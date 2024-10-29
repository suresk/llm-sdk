package net.uresk.ai.llm.exceptions;

public class ApiKeyException extends RuntimeException
{

    public ApiKeyException()
    {
        super("Incorrect API key - check API key string");
    }

}
