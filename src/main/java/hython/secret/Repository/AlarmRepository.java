package hython.secret.Repository;

import hython.secret.Entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Integer> {

    Alarm findByAlarmName(String alarmName);
}
