package cn.fufeii.ds.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author FuFei
 * @date 2022/3/28
 */
public final class ResponseUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private ResponseUtil() {
    }

    public static void write(HttpServletResponse response, Object data) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try {
            response.getWriter().write(OBJECT_MAPPER.writeValueAsString(data));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
