package joey.websocket.stomp.listener;

import joey.websocket.stomp.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.*;

/**
 *
 */
@Component
@Slf4j
public class WebSocketEventListener {

    @Autowired
    private SimpMessageSendingOperations msgTemplate;

    @EventListener
    public void onConnect(SessionConnectEvent event) {
        log.info("onConnect");
    }

    @EventListener
    public void onConnected(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        log.info("onConnected");
    }

    @EventListener
    public void onSub(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        log.info("onSub");
    }

    @EventListener
    public void onUnsubscribe(SessionUnsubscribeEvent event) {
        log.info("onUnsubscribe");
    }

    @EventListener
    public void onDisconnect(SessionDisconnectEvent event) {
        log.info("onDisconnect");

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get(Constants.U_TOKEN);
        if(username != null) {
            log.info("User Disconnected : " + username);
        }
    }
}
