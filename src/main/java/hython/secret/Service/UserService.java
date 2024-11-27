package hython.secret.Service;

import hython.secret.DTO.UserDTO;
import hython.secret.Entity.User;
import hython.secret.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
