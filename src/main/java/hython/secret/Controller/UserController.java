package hython.secret.Controller;


import hython.secret.API.ApiResponseDTO;
import hython.secret.DTO.FriendShipDTO;
import hython.secret.DTO.WaitingFriendListDTO;
import hython.secret.Repository.UserRepository;
import hython.secret.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/friends/{userCode}")
    public ResponseEntity<ApiResponseDTO<Void>> sendFriendsRequest(@RequestBody FriendShipDTO requestDTO) throws Exception {
        try {
            if (userService.existsByUser(requestDTO)) {
                String userCode = requestDTO.getUserCode();
                userService.addFriendShip(userCode);
                log.info("친구 요청 완료");
                return ResponseEntity.ok(new ApiResponseDTO<>("200", "친구 요청이 성공적으로 전송되었습니다.", null, null));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ApiResponseDTO<>("400", "해당 유저가 존재하지 않습니다.", null, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponseDTO<>("500", "친구 요청 처리 중 오류가 발생했습니다: " + e.getMessage(), null, null));
        }
    }

    @GetMapping("/friends/received")
    public ResponseEntity<ApiResponseDTO<List<WaitingFriendListDTO>>> getFriends() throws Exception {

        List<WaitingFriendListDTO> waitingFriendList = userService.getWaitingFriendList();

        ApiResponseDTO<List<WaitingFriendListDTO>> response = new ApiResponseDTO<>("200", "대기 중인 친구 요청을 조회했습니다.", waitingFriendList, null);

        return ResponseEntity.ok(response);

    }

    // 친구 요청 수락
    @PutMapping("/friends/approve/{friendId}")
    public ResponseEntity<ApiResponseDTO<Void>> approveFriend(@PathVariable("friendId") int friendId){
        try {
            if (userService.approveRequest(friendId)) {
                log.info("친구 요청을 성공적으로 수락되었습니다.");
                return ResponseEntity.ok(new ApiResponseDTO<>("200", "친구 요청이 성공적으로 수락되었습니다.", null, null));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponseDTO<>("400", "해당 친구 요청을 찾을 수 없습니다.", null, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponseDTO<>("500", "친구 요청 수락 중 오류가 발생했습니다: " + e.getMessage(), null, null));
        }
    }


    /**
     * 친구 찾기
     */
    @Operation(summary = "사용자 찾기 api", description = "사용자를 찾을 수 있습니다.")
    @PostMapping("/find")
    public ResponseEntity<ApiResponseDTO<String>> findUser(@RequestBody FriendShipDTO requestDTP){
        if(userService.existsByUser(requestDTP)){
            String userCode = requestDTP.getUserCode();
            String nickname = requestDTP.getNickname();
            log.info("사용자를 찾았습니다.{}, {}", userCode, nickname);
            return ResponseEntity.ok(new ApiResponseDTO<>("200", "유저를 찾았습니다.", userCode, nickname));

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponseDTO<>("400", "해당 사용자를 찾을 수 없습니다.", null, null));    }
}
