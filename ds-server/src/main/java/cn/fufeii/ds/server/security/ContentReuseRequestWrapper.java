package cn.fufeii.ds.server.security;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 可重用的请求包装器
 * 解决某些情况下需要重复消费请求体（request-content）的场景
 *
 * @author FuFei
 */
public class ContentReuseRequestWrapper extends HttpServletRequestWrapper {
    /**
     * 请求体内容
     */
    private final byte[] content;
    /**
     * 请求体的编码
     */
    private final Charset characterEncoding;


    /**
     * 如果原请求的流没有被消费，则此构造器自动消费
     */
    public ContentReuseRequestWrapper(HttpServletRequest request) {
        super(request);
        this.characterEncoding = StrUtil.isBlank(request.getCharacterEncoding()) ? StandardCharsets.UTF_8 : Charset.forName(request.getCharacterEncoding());
        try {
            // 提前缓存请求体数据
            this.content = IoUtil.readBytes(request.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 如果原请求的流已被消费，则需要外部自行传入
     */
    public ContentReuseRequestWrapper(HttpServletRequest request, byte[] content) {
        super(request);
        this.characterEncoding = StrUtil.isBlank(request.getCharacterEncoding()) ? StandardCharsets.UTF_8 : Charset.forName(request.getCharacterEncoding());
        // 提前缓存请求体数据
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }

    @Override
    public ServletInputStream getInputStream() {
        // 创建一个全新的流来包装content
        return new ContentReuseServletInputStream(content);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        // 创建一个全新的reader来包装content
        return new BufferedReader(new StringReader(new String(content, characterEncoding)));
    }


    /**
     * 此流包装了一段内容
     * 需要的时候新建一个全新的流来包装内容
     * 从而实现重用
     */
    private static class ContentReuseServletInputStream extends ServletInputStream {

        private final InputStream inputStream;

        private ContentReuseServletInputStream(byte[] content) {
            // 将请求体内容封装为输入流
            inputStream = new ByteArrayInputStream(content);
        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener listener) {

        }
    }
}