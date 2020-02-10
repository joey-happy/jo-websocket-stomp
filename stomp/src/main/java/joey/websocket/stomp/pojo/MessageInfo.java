package joey.websocket.stomp.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 传输消息载体
 *
 * @author Joey
 * @date 2019/12/19
 */
@Data
public class MessageInfo implements Serializable {
    private MessageAction action;
    private String content;
    private String sender;

    public enum MessageAction {
        JOIN,
        LEAVE,
        SEND;
    }
}
