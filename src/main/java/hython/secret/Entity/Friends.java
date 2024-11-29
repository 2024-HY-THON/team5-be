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
    private int id;     //친구의 인덱스


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;  //친구의 유저 정보

    @Column
    private String userCode;    //본인의 닉네임 뒤의 #해시태그

    @Column
    private String friendCode;  //친구의 닉네임 뒤 해시태그

    @Column
    private String friendNickName;  //친구 닉네임

    @Enumerated(EnumType.STRING)
    private FriendShipStatus status;    //친구의 상태


    @Column
    private boolean isFrom;

    @Column
    private int counterId;
    // 상대 요청의 아이디
}
