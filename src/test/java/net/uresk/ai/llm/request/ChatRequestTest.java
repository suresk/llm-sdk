package net.uresk.ai.llm.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.uresk.ai.llm.content.ImageContent;
import net.uresk.ai.llm.messages.UserMessage;
import net.uresk.ai.llm.models.OpenAI;
import net.uresk.ai.llm.utils.MapperUtil;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.InputStream;
import java.util.List;

public class ChatRequestTest
{

    private ObjectMapper mapper;

    @BeforeEach
    public void setup()
    {
        mapper = MapperUtil.getJsonMapper();
    }

    private void assertJson(String fileName, ChatRequest request) throws Exception
    {
        InputStream is = this.getClass().getResourceAsStream(fileName);
        JSONTokener tokener = new JSONTokener(is);
        JSONObject json = new JSONObject(tokener);

        var str = mapper.writeValueAsString(request);
        JSONAssert.assertEquals(json.toString(), str, true);
    }

    @Test
    public void testSimpleMessage() throws Exception
    {
        var req = new ChatRequest.Builder().model(OpenAI.GPT40).userMessages(List.of(new UserMessage("Hello, how are you?"))).build();
        assertJson("/testcases/simple.json", req);
    }

    @Test
    public void testImageBytes() throws Exception
    {
        UserMessage msg = new UserMessage("What is in this image?");

        InputStream is = this.getClass().getResourceAsStream("/small.png");
        byte[] bytes = is.readAllBytes();

        UserMessage img = new UserMessage(ImageContent.fromBytes(bytes, "image/png"));

        var req = new ChatRequest.Builder().model(OpenAI.GPT40).userMessages(List.of(msg, img)).build();
        assertJson("/testcases/imagebytes.json", req);
    }

    @Test
    public void testImageURL() throws Exception
    {
        UserMessage msg = new UserMessage("What is in this image?");

        UserMessage img = new UserMessage(ImageContent.fromUrl("https://example.com/image.png"));

        var req = new ChatRequest.Builder().model(OpenAI.GPT40).userMessages(List.of(msg, img)).build();
        assertJson("/testcases/imageurl.json", req);
    }

}
