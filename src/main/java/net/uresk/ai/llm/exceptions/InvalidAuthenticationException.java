package net.uresk.ai.llm.exceptions;

import net.uresk.ai.llm.response.Metadata;

public class InvalidAuthenticationException extends ApiException
{

    public InvalidAuthenticationException(Metadata metadata)
    {
        super("Invalid authentication - ensure you are sending the correct API key header (Authentication: Bearer <key>)", metadata);
    }

}
