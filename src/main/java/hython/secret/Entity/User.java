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
    private int user_id;

    @Column(nullable = false)
    private String username;

    @Column
    private String nickName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String platform;

    @Column(nullable = false)
    private String platform_id;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime joinDate;

    @Column(nullable = false)
    private int userCode;

    @OneToMany(mappedBy = "user")
    private List<Friends> friends;

    @OneToMany(mappedBy = "friend")
    private List<Friends> inverseFriends; // 자신을 친구로 추가한 사용자 목록

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Belog> belogs;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Award> award;
}
