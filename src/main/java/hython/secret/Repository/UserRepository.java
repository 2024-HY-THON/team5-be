package hython.secret.Repository;

import hython.secret.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User save(User user);
    User findByEmail(String email);
    User findByUserCode(String userCode);
    boolean existsByEmail(String email);
    boolean existsByNickName(String NickName);
    boolean existsByUserCode(String userCode);


    @Query("SELECT COUNT(a) FROM Award a WHERE a.user.userId = :userId")
    int countAwardsByUserId(@Param("userId") int userId);

    @Query("SELECT COUNT(b) FROM Belog b WHERE b.user.userId = :userId")
    int countBelogsByUserId(@Param("userId") int userId);
}
