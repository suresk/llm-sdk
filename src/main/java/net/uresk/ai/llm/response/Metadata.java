package net.uresk.ai.llm.response;

public class Metadata
{

    private String organization;

    private Long processingTime;

    private String apiVersion;

    private String requestId;

    private RateLimitInfo rateLimitInfo;

    public Metadata(String organization, Long processingTime, String apiVersion, String requestId, RateLimitInfo rateLimitInfo)
    {
        this.organization = organization;
        this.processingTime = processingTime;
        this.apiVersion = apiVersion;
        this.requestId = requestId;
        this.rateLimitInfo = rateLimitInfo;
    }

    public String getOrganization()
    {
        return organization;
    }

    public Long getProcessingTime()
    {
        return processingTime;
    }

    public String getApiVersion()
    {
        return apiVersion;
    }

    public String getRequestId()
    {
        return requestId;
    }

    public RateLimitInfo getRateLimitInfo()
    {
        return rateLimitInfo;
    }
}
