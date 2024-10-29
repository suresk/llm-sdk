package net.uresk.ai.llm.exceptions;

public class ServerOverloadedException extends RuntimeException
{

    public ServerOverloadedException()
    {
        super("Server overloaded - try again later");
    }

}
