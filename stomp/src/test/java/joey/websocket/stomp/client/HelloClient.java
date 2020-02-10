package joey.websocket.stomp.client;

import com.alibaba.fastjson.JSON;
import joey.websocket.stomp.pojo.MessageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * Created by nick on 30/09/2015.
 */
@Slf4j
public class HelloClient {
    private final static WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

    public ListenableFuture<StompSession> connect() {
//        WebSocketClient webSocketClient = new StandardWebSocketClient();
//        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
//        stompClient.setMessageConverter(new StringMessageConverter());
//        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
//        stompClient.setTaskScheduler(new DefaultManagedTaskScheduler());

        Transport webSocketTransport = new WebSocketTransport(new StandardWebSocketClient());
        List<Transport> transports = Collections.singletonList(webSocketTransport);

        SockJsClient sockJsClient = new SockJsClient(transports);
        sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());

        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

//        String url = "ws://{host}:{port}/traffic?uToken=" + RandomUtil.randomString(5);
//        return stompClient.connect(url, headers, new MyHandler(), "localhost", 9999);
        String url = "ws://localhost:9999/traffic?uToken=" + UUID.randomUUID().toString();
        return stompClient.connect(url, new MyHandler());
    }

    public void subscribeGreetings(StompSession stompSession) throws ExecutionException, InterruptedException {
        stompSession.subscribe("/topic/message/1", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return MessageInfo.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println(payload.toString());
            }
        });
    }

    public void sendHello(StompSession stompSession) {
        MessageInfo vo = new MessageInfo();
        vo.setAction(MessageInfo.MessageAction.SEND);
        vo.setContent("test");
        vo.setSender("me");
        stompSession.send("/app/message/1", vo);
    }

    private class MyHandler extends StompSessionHandlerAdapter {
        public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
            log.info("Now connected");
        }

        public MyHandler() {
            super();
        }

        @Override
        public Type getPayloadType(StompHeaders headers) {
            return super.getPayloadType(headers);
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            super.handleFrame(headers, payload);
        }

        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
            super.handleException(session, command, headers, payload, exception);
        }

        @Override
        public void handleTransportError(StompSession session, Throwable exception) {
            super.handleTransportError(session, exception);
        }
    }
    
    public static void main(String[] args) throws Exception {
        HelloClient helloClient = new HelloClient();

        ListenableFuture<StompSession> f = helloClient.connect();
        StompSession stompSession = f.get();

        log.info("Subscribing to greeting topic using session " + stompSession);
        helloClient.subscribeGreetings(stompSession);

        log.info("Sending hello message" + stompSession);
        helloClient.sendHello(stompSession);
        new CountDownLatch(1).await();
    }
}
