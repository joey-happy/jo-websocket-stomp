package joey.websocket.stomp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionSerive {
    private final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    public void register(WebSocketSession session) {
        sessionMap.put(session.getPrincipal().getName(), session);
    }
 
    public void remove(WebSocketSession session) {
        sessionMap.remove(session.getPrincipal().getName());
    }
 
    public boolean isOnline(String userId){
        return sessionMap.get(userId) != null;
    }
}