package hython.secret.Repository;

import hython.secret.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User save(User user);
    User findByEmail(String email);
    boolean existsByEmail(String email);
}
