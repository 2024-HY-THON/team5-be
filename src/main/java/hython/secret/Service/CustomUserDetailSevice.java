package hython.secret.Service;

import hython.secret.DTO.CustomUserDetail;
import hython.secret.Entity.User;
import hython.secret.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailSevice implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailSevice(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User findEmail = userRepository.findByEmail(email);

        if (findEmail != null) {
            return new CustomUserDetail(findEmail, null);
        }
        return null;
    }
}
