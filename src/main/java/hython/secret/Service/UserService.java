package hython.secret.Service;

import hython.secret.DTO.FriendShipDTO;
import hython.secret.DTO.UserDTO;
import hython.secret.DTO.WaitingFriendListDTO;
import hython.secret.Entity.FriendShipStatus;
import hython.secret.Entity.Friends;
import hython.secret.Entity.User;
import hython.secret.Repository.FriendsRepository;
import hython.secret.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final FriendsRepository friendsRepository;

    public UserService(UserRepository userRepository, FriendsRepository friendsRepository) {
        this.userRepository = userRepository;
        this.friendsRepository = friendsRepository;
    }

    @Transactional
    public User saveUser(UserDTO userDTO){

        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

        if(userDTO.getRole() == null){
            userDTO.setRole("ROLE_USER");
        }

        User user = UserConverter.toEntity(userDTO);

        userRepository.save(user);

        return userRepository.findByEmail(userDTO.getEmail());
    }

    public boolean existsByUser(FriendShipDTO friendShipDTO){
        String nickname = friendShipDTO.getNickname();
        String userCode = friendShipDTO.getUserCode();

        if(userRepository.existsByNickname(nickname)){
            if(userRepository.existsByUserCode(userCode)){
                return true;
            } else{
                log.info("해당하는 usercode가 없습니다.");
                return false;
            }
        } else{
            log.info("해당하는 Nickname이 없습니다.");
            return false;
        }
    }

    public void addFriendShip(String userCode, Principal principal) throws Exception {

        String fromEmail = principal.getName(); // 현재 로그인 되어있는 사람

        User fromUser = userRepository.findByEmail(fromEmail);
        String fromCode = fromUser.getUserCode();
        String fromNickname = fromUser.getNickName();
        User toUser = userRepository.findByUserCode(userCode);
        String toNickname = toUser.getNickName();

        if (fromUser.getFriends().contains(toUser)){
            log.info("이미 친구 관계입니다.");
            throw new Exception("이미 친구 관계입니다.");
        }

        Friends friendsFrom = new Friends();
        friendsFrom.setUser(fromUser);
        friendsFrom.setUserCode(fromCode);
        friendsFrom.setFriendNickName(fromNickname);
        friendsFrom.setStatus(FriendShipStatus.WAITING);
        friendsFrom.setFrom(true); // 보내는 사람

        Friends friendsTo = new Friends();
        friendsTo.setUser(toUser);
        friendsTo.setUserCode(userCode);
        friendsTo.setFriendNickName(toNickname);
        friendsTo.setStatus(FriendShipStatus.WAITING);
        friendsTo.setFrom(false);  // 받는 사람

        // 각 유저 리스트에 친구 요청 추가
        fromUser.getFriends().add(friendsFrom);
        toUser.getFriends().add(friendsTo);

        friendsRepository.save(friendsFrom);
        friendsRepository.save(friendsTo);

        // 매칭되는 친구 요청의 ID를 서로 저장
        friendsFrom.setCounterId(friendsTo.getId());
        friendsTo.setCounterId(friendsFrom.getId());

        userRepository.save(fromUser);
        userRepository.save(toUser);
    }

    public List<WaitingFriendListDTO> getWaitingFriendList(Principal principal) throws Exception {

        User user = userRepository.findByEmail(principal.getName());
        List<Friends> friendsList = user.getFriends();

        // 조회된 결과 객체를 담을 DTO
        List<WaitingFriendListDTO> result = new ArrayList<>();

        for(Friends x : friendsList) {
            if (!x.isFrom() && x.getStatus() == FriendShipStatus.WAITING) {
                User friend = userRepository.findByUserCode(x.getUserCode());
                WaitingFriendListDTO dto = new WaitingFriendListDTO();
                dto.setFriendshipId(x.getId());
                dto.setFriendCode(friend.getUserCode());
                dto.setStatus(x.getStatus());
                dto.setFriendNickName(x.getFriendNickName());

                result.add(dto);
            }
        }
        return result;
    }

    public boolean approveRequest(int friendId){
        Friends friendship = friendsRepository.findById(friendId);
        Friends counterFriends = friendsRepository.findById((friendship.getCounterId()));

        friendship.setStatus(FriendShipStatus.ACCEPT);
        counterFriends.setStatus(FriendShipStatus.ACCEPT);

        return true;
    }


}
