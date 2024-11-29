package hython.secret.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    private int id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String userCode;

    @Column
    private String friendCode;

    @Column
    private String friendNickName;

    @Column
    private FriendShipStatus status;

    @Column
    private boolean isFrom;
    @Column
    private int counterId;
    // 상대 요청의 아이디
}
