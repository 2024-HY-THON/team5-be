package hython.secret.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Getter
@Setter
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int friend_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_user_id", nullable = false)
    private User friend;

    @ColumnDefault("false")
    private boolean is_accepted;

    @ColumnDefault("false")
    private boolean is_blocked;

    public Friends() {
        this.is_accepted = false;
        this.is_blocked = false;
    }
}
