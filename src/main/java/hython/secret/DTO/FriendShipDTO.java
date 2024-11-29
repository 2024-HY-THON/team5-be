package hython.secret.DTO;

import hython.secret.Entity.FriendShipStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendShipDTO {

    private String nickname;
    private String userCode;
    private FriendShipStatus status;
    private boolean isFrom;
    private String friendCode;

}
