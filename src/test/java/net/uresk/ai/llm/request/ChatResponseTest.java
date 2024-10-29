package net.uresk.ai.llm.request;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.uresk.ai.llm.response.ChatResponse;
import net.uresk.ai.llm.response.FinishReason;
import net.uresk.ai.llm.utils.MapperUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChatResponseTest
{

    private ObjectMapper mapper;

    @BeforeEach
    public void setup()
    {
        mapper = MapperUtil.getJsonMapper();
    }

    @Test
    public void testNormalResponse() throws Exception
    {
        ChatResponse response = mapper.readValue(this.getClass().getResourceAsStream("/testcases/response-normal.json"), ChatResponse.class);

        Assertions.assertEquals("It is a tree!", response.getChoices().get(0).getMessage().getContent());
        Assertions.assertEquals(FinishReason.STOP, response.getChoices().get(0).getFinishReason());
    }

    @Test
    public void testToolCalls() throws Exception
    {
        ChatResponse response = mapper.readValue(this.getClass().getResourceAsStream("/testcases/response-toolcalls.json"), ChatResponse.class);

        Assertions.assertEquals("It is a tree!", response.getChoices().get(0).getMessage().getContent());
        Assertions.assertEquals(FinishReason.TOOL_CALLS, response.getChoices().get(0).getFinishReason());
    }

}
