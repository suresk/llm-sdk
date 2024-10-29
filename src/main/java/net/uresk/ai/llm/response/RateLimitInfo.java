package net.uresk.ai.llm.response;

public class RateLimitInfo
{

    private Long requestLimit;

    private Long tokenLimit;

    private Long tokensRemaining;

    private Long requestsRemaining;

    private Long tokenResetTime;

    private Long requestResetTime;

    public RateLimitInfo(Long requestLimit, Long tokenLimit, Long tokensRemaining, Long requestsRemaining, Long tokenResetTime, Long requestResetTime)
    {
        this.requestLimit = requestLimit;
        this.tokenLimit = tokenLimit;
        this.tokensRemaining = tokensRemaining;
        this.requestsRemaining = requestsRemaining;
        this.tokenResetTime = tokenResetTime;
        this.requestResetTime = requestResetTime;
    }

    public Long getRequestLimit()
    {
        return requestLimit;
    }

    public Long getTokenLimit()
    {
        return tokenLimit;
    }

    public Long getTokensRemaining()
    {
        return tokensRemaining;
    }

    public Long getRequestsRemaining()
    {
        return requestsRemaining;
    }

    public Long getTokenResetTime()
    {
        return tokenResetTime;
    }

    public Long getRequestResetTime()
    {
        return requestResetTime;
    }
}
