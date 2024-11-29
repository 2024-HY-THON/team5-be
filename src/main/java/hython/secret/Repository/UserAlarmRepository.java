package hython.secret.Repository;

import hython.secret.Entity.User_alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAlarmRepository extends JpaRepository<User_alarm, Integer> {
    User_alarm save(User_alarm user_alarm);
}
