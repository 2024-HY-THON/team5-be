package hython.secret.Service;

import hython.secret.DTO.UserDTO;
import hython.secret.Entity.User;

public class UserConverter {

    public static UserDTO toDTO(User user) {

        return new UserDTO(
                user.getPlatform(),
                user.getPlatform_id(),
                user.getUserCode(),
                user.getRole(),
                user.getUsername(),
                user.getEmail()
        );
    }

    public static User toEntity(UserDTO userDTO) {

        User user = new User();

        if (userDTO.getEmail() == null){
            throw new IllegalArgumentException("이메일이 null일 수 없습니다.");
        }

        user.setPlatform(userDTO.getPlatform());
        user.setPlatform_id(userDTO.getPlatform_id());
        user.setUserCode(userDTO.getUserCode());
        user.setRole(userDTO.getRole());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        return user;
    }
}
