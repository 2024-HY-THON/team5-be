package hython.secret.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column
    private String userCode;

    @Column
    private String username;

    @Column
    private String nickName;

    @Column
    private String email;

    @Column
    private String role;

    @Column
    private String platform;

    @Column
    private String platform_id;

    @CreationTimestamp
    @Column
    private LocalDateTime joinDate;


    @OneToMany(mappedBy = "user")
    private List<Friends> friends;

    @OneToMany(mappedBy = "friend")
    private List<Friends> inverseFriends; // 자신을 친구로 추가한 사용자 목록

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Belog> belogs;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Award> award;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User_alarm> userAlarms; // 사용자-알림 중간 테이블 매핑


}
