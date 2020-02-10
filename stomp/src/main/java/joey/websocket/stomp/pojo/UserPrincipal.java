package joey.websocket.stomp.pojo;

import lombok.AllArgsConstructor;

import java.security.Principal;

/**
 * @author Joey
 * @date 2019/12/20
 */
@AllArgsConstructor
public class UserPrincipal implements Principal {
    private final String name;

    @Override
    public String getName() {
        return name;
    }
}
