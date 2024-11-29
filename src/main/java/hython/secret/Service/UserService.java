package hython.secret.Service;

import hython.secret.DTO.FriendShipDTO;
import hython.secret.DTO.UserDTO;
import hython.secret.DTO.UserStatsDTO;
import hython.secret.DTO.WaitingFriendListDTO;
import hython.secret.Entity.FriendShipStatus;
import hython.secret.Entity.Friends;
import hython.secret.Entity.User;
import hython.secret.Repository.FriendsRepository;
import hython.secret.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        if(userRepository.existsByNickName(nickname)){
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

    public void addFriendShip(String userCode) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String fromEmail = authentication.getName();  // 현재 로그인된 사용자의 이메일

        User fromUser = userRepository.findByEmail(fromEmail);
        if (fromUser == null) {
            throw new Exception("해당 사용자를 찾을 수 없습니다: " + fromEmail);
        }
        String fromCode = fromUser.getUserCode();
        String fromNickname = fromUser.getNickName();
        // userCode에 해당하는 사용자가 없을 경우 예외 처리
        User toUser = userRepository.findByUserCode(userCode);
        if (toUser == null) {
            throw new Exception("해당 사용자를 찾을 수 없습니다: " + userCode);
        }
        String toNickname = toUser.getNickName();

        if (fromUser.getFriends().contains(toUser)){
            log.warn("이미 친구 관계입니다: {} -> {}", fromUser.getEmail(), toUser.getEmail());
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
        log.info("각 유저 리스트에 친구 요청 추가");
        
        // 매칭되는 친구 요청의 ID를 서로 저장
        friendsFrom.setCounterId(friendsTo.getId());
        friendsTo.setCounterId(friendsFrom.getId());

        log.info("친구 요청의 ID를 서로 저장");
        userRepository.save(fromUser);
        userRepository.save(toUser);
    }

    public List<WaitingFriendListDTO> getWaitingFriendList() throws Exception {
        // SecurityContextHolder를 통해 로그인한 사용자의 이메일 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String fromEmail = authentication.getName();  // 현재 로그인된 사용자의 이메일

        User user = userRepository.findByEmail(fromEmail);
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
        Optional<Friends> friendship = friendsRepository.findById(friendId);
        Optional<Friends> counterFriends = friendsRepository.findById((friendship.get().getCounterId()));

        friendship.get().setStatus(FriendShipStatus.ACCEPT);
        counterFriends.get().setStatus(FriendShipStatus.ACCEPT);

        return true;
    }

    // 현재 로그인된 사용자의 ID 반환
    public int getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            throw new IllegalStateException("현재 인증된 사용자가 없습니다.");
        }

        // 인증된 사용자라면 Principal에서 ID를 반환
        return Integer.parseInt(authentication.getPrincipal().toString());
    }
}
