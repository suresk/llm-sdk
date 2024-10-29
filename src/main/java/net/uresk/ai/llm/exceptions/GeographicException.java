package net.uresk.ai.llm.exceptions;

import net.uresk.ai.llm.response.Metadata;

public class GeographicException extends ApiException
{

    public GeographicException(Metadata metadata)
    {
        super("Country, region, or territory unsupported. See: https://platform.openai.com/docs/supported-countries", metadata);
    }

}
