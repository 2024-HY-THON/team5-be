package hython.secret.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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

    @Column(nullable = false)
    private Date joinDate;

    @Column(nullable = false)
    private boolean is_status;
}
