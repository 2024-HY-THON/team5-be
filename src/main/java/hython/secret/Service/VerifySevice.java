package hython.secret.Service;

import hython.secret.Entity.User;
import hython.secret.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class VerifySevice {

    private final UserRepository userRepository;

    public VerifySevice(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean updateNickname(String nickname, String email){

        User user = userRepository.findByEmail(email);

        if(user != null){
            user.setNickName(nickname);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
