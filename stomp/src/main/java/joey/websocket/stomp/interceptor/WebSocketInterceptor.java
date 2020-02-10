package joey.websocket.stomp.interceptor;

import joey.websocket.stomp.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @author Joey
 * @Data 2020/02/10
 *
 * webSocket拦截器
 */
@Slf4j
public class WebSocketInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        boolean  result = false;

        String uToken = getToken(serverHttpRequest);
        log.info("WebSocketInterceptor-beforeHandshake.uToken={}", uToken);

        //session校验
        if (StringUtils.isNotBlank(uToken)) {
            map.put(Constants.U_TOKEN, uToken);
            result = true;
        }

        return result;
    }

    private String getToken(ServerHttpRequest serverHttpRequest) {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            return ((ServletServerHttpRequest) serverHttpRequest).getServletRequest().getParameter("uToken");
        }

        return null;
    }

//    private HttpSession getSession(ServerHttpRequest request) {
//        if (request instanceof ServletServerHttpRequest) {
//            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
//            return serverRequest.getServletRequest().getSession();
//        }
//
//        return null;
//    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        String uToken = getToken(serverHttpRequest);
        log.info("WebSocketInterceptor-afterHandshake.uToken={}", uToken);
    }
}

