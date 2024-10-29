package net.uresk.ai.llm.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ResponseFormat
{

    @JsonProperty("text")
    TEXT("text"),
    @JsonProperty("json_object")
    JSON_OBJECT("json_object"),
    @JsonProperty("json_schema")
    JSON_SCHEMA("json_schema");

    public final String name;

    ResponseFormat(String name)
    {
        this.name = name;
    }

}
