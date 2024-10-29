package net.uresk.ai.llm.exceptions;

public class OpenAIServiceError extends RuntimeException
{

    public OpenAIServiceError()
    {
        super("Server error. See status page: https://status.openai.com/");
    }

}
