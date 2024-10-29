package net.uresk.ai.llm.exceptions;

import net.uresk.ai.llm.response.Metadata;

public class ApiException extends RuntimeException
{

    private Metadata metadata;

    public ApiException(String message, Metadata metadata)
    {
        super(String.format("(Request id: %s) %s", metadata.getRequestId(), message));
        this.metadata = metadata;
    }

    public Metadata getMetadata()
    {
        return metadata;
    }
}
