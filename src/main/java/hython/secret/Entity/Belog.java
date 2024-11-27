package hython.secret.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Belog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int belogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String content;

    @CreationTimestamp
    @Column
    private LocalDateTime create_at;

    @UpdateTimestamp
    @Column
    private LocalDateTime update_at;

    @ColumnDefault("false")
    private boolean is_anonymous;
}
