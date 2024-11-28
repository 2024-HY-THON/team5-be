package hython.secret.Controller;


import hython.secret.API.ApiResponseDTO;
import hython.secret.DTO.FriendShipDTO;
import hython.secret.DTO.WaitingFriendListDTO;
import hython.secret.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/friends/{userCode}")
    public ResponseEntity<ApiResponseDTO<Void>> sendFriendsRequest(@RequestBody FriendShipDTO requestDTO,
                                                                   Principal principal) throws Exception {
        try {
            // 존재하는 유저인지 확인
            if (userService.existsByUser(requestDTO)) {
                String userCode = requestDTO.getUserCode();

                // 친구 요청 추가
                userService.addFriendShip(userCode, principal);

                return ResponseEntity.ok(new ApiResponseDTO<>("200", "친구 요청이 성공적으로 전송되었습니다.", null));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ApiResponseDTO<>("400", "해당 유저가 존재하지 않습니다.", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponseDTO<>("500", "친구 요청 처리 중 오류가 발생했습니다: " + e.getMessage(), null));
        }
    }

    @GetMapping("/friends/received")
    public ResponseEntity<ApiResponseDTO<List<WaitingFriendListDTO>>> getFriends(Principal principal) throws Exception {

        List<WaitingFriendListDTO> waitingFriendList = userService.getWaitingFriendList(principal);

        ApiResponseDTO<List<WaitingFriendListDTO>> response = new ApiResponseDTO<>("200", "대기 중인 친구 요청을 조회했습니다.", waitingFriendList);

        return ResponseEntity.ok(response);

    }

    @PostMapping("/friends/approve/{friend_id}")
    public ResponseEntity<ApiResponseDTO<Void>> approveFriend(@PathVariable("friend_id") int friend_id){
        if(userService.approveRequest(friend_id)){
            log.info("친구 요청을 성공적으로 수락되었습니다.");
            return ResponseEntity.ok(new ApiResponseDTO<>("200", "친구 요청이 성공적으로 수락되었습니다.", null));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponseDTO<>("400", "해당 유저가 존재하지 않습니다.", null));    }
}
