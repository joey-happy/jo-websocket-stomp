package joey.websocket.stomp.controller;

import joey.websocket.stomp.pojo.MessageInfo;
import joey.websocket.stomp.service.SessionSerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

/**
 * @author Joey
 * @date 2020/2/10
 */
@RestController
@RequestMapping("/message")
@MessageMapping("/message")
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate msgTemplate;

    @Autowired
    private SessionSerive sessionSerive;

    @MessageMapping("/{roomId}")
    public void sendMessage(@Payload MessageInfo message, @DestinationVariable String roomId, Principal principal)  {
        System.out.println(message.toString() + " " + roomId);
        msgTemplate.convertAndSend("/topic/message/" + roomId, message);

    }

    @RequestMapping("/{roomId}")
    public void sendMessage(@PathVariable("roomId") String roomId)  {
        MessageInfo vo = new MessageInfo();
        vo.setContent(UUID.randomUUID().toString());

        msgTemplate.convertAndSend("/topic/message/" + roomId, vo);

    }


//    @MessageMapping("/message/{roomId}")
//    public void sub(@Payload MessageInfo message, Principal principal)  {
//
//        JSONObject obj=new JSONObject(str);
//        String openid=obj.getString("name");
//        SMT.convertAndSendToUser(openid,"/topic/chat",
//                "消息："+obj.getString("content")+" from "+principal.getName());//发送消息到指定用户
//        boolean online=sessionHandler.isOnline(openid);
//
//        System.out.println(openid+" "+online);
//    }
}
