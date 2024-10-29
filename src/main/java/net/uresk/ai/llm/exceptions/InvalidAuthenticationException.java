package net.uresk.ai.llm.exceptions;

public class InvalidAuthenticationException extends RuntimeException
{

    public InvalidAuthenticationException()
    {
        super("Invalid authentication - ensure you are sending the correct API key header (Authentication: Bearer <key>)");
    }

}
