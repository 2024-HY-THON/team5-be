package hython.secret.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int alarmId;

    @Column
    private String alarmName;

    @Column
    private boolean is_alarm;

    @OneToMany(mappedBy = "alarm", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User_alarm> userAlarms; // 사용자-알림 중간 테이블 매핑
}
