package net.uresk.ai.llm.exceptions;

public class GeographicException extends RuntimeException
{

    public GeographicException()
    {
        super("Country, region, or territory unsupported. See: https://platform.openai.com/docs/supported-countries");
    }

}
