package hython.secret.DTO;

import hython.secret.Entity.FriendShipStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WaitingFriendListDTO {
    private int friendshipId; // 친구 요청의 고유 ID
    private String friendCode; // 친구의 고유번호
    private String friendNickName; // 친구의 닉네임
    private FriendShipStatus status;  // 친구 요청 상태
}
