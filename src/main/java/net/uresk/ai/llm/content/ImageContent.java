package net.uresk.ai.llm.content;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageContent extends Content
{

    @JsonProperty("image_url")
    private UrlWrapper url;

    private String detail;

    public ImageContent(String url, String detail)
    {
        super("image_url");
        this.url = new UrlWrapper(url, detail);
    }

    public UrlWrapper getUrl()
    {
        return url;
    }

    public static ImageContent fromBytes(byte[] bytes, String contentType)
    {
        return fromBytes(bytes, contentType, "auto");
    }

    public static ImageContent fromBytes(byte[] bytes, String contentType, String detail)
    {
        return new ImageContent(String.format("data:%s;base64,%s", contentType, new String(Base64.getEncoder().encode(bytes))), detail);
    }

    public static ImageContent fromPath(String path, String contentType)
    {
        return fromPath(path, contentType, "auto");
    }

    public static ImageContent fromPath(String path, String contentType, String detail)
    {
        try
        {
            return new ImageContent(String.format("data:%s;base64,%s", contentType, Base64.getEncoder().encodeToString(Files.readAllBytes(Path.of(path)))), detail);
        }
        catch(IOException e)
        {
            throw new RuntimeException(String.format("Error reading file at %s", path), e);
        }
    }

    public static ImageContent fromUrl(String url)
    {
        return fromUrl(url, "auto");
    }

    public static ImageContent fromUrl(String url, String detail)
    {
        return new ImageContent(url, detail);
    }

    private static class UrlWrapper
    {
        @JsonProperty("url")
        private String url;

        @JsonProperty("detail")
        private String detail;

        public UrlWrapper(String url, String detail)
        {
            this.url = url;
            this.detail = detail;
        }

        public String getUrl()
        {
            return url;
        }

        public void setUrl(String url)
        {
            this.url = url;
        }

        public String getDetail()
        {
            return detail;
        }

        public void setDetail(String detail)
        {
            this.detail = detail;
        }
    }

}
