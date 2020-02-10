package joey.websocket.stomp.handler;

import joey.websocket.stomp.constant.Constants;
import joey.websocket.stomp.pojo.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * @author Joey
 * @date 2019/12/19
 */
@Slf4j
public class WebSocketHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        Object user = attributes.get(Constants.U_TOKEN);

        return new UserPrincipal(user.toString());
    }
}
