package net.uresk.ai.llm.request;

public class JsonSchemaResponseFormatObject extends ResponseFormatObject
{

    private Object jsonSchema;

    public JsonSchemaResponseFormatObject(Object jsonSchema)
    {
        super(ResponseFormat.JSON_SCHEMA);
        this.jsonSchema = jsonSchema;
    }

    public Object getJsonSchema()
    {
        return jsonSchema;
    }

    public void setJsonSchema(Object jsonSchema)
    {
        this.jsonSchema = jsonSchema;
    }
}
