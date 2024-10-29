package net.uresk.ai.llm.exceptions;

import net.uresk.ai.llm.response.Metadata;

public class ServerOverloadedException extends ApiException
{

    public ServerOverloadedException(Metadata metadata)
    {
        super("Server overloaded - try again later", metadata);
    }

}
