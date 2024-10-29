package net.uresk.ai.llm.content;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class ImageContent extends Content
{

    @JsonProperty("image_url")
    private UrlWrapper url;

    public ImageContent(String url)
    {
        super("image_url");
        this.url = new UrlWrapper(url);
    }

    public UrlWrapper getUrl()
    {
        return url;
    }

    public static ImageContent fromBytes(byte[] bytes, String contentType)
    {
        return new ImageContent(String.format("data:%s;base64,%s", contentType, new String(Base64.getEncoder().encode(bytes))));
    }

    public static ImageContent fromPath(String path, String contentType)
    {
        try
        {
            return new ImageContent(String.format("data:%s;base64,%s", contentType, Base64.getEncoder().encodeToString(Files.readAllBytes(Path.of(path)))));
        }
        catch(IOException e)
        {
            throw new RuntimeException(String.format("Error reading file at %s", path), e);
        }
    }

    public static ImageContent fromUrl(String url)
    {
        return new ImageContent(url);
    }

    private static class UrlWrapper
    {
        @JsonProperty("url")
        private String url;

        public UrlWrapper(String url)
        {
            this.url = url;
        }

        public String getUrl()
        {
            return url;
        }

        public void setUrl(String url)
        {
            this.url = url;
        }
    }

}
