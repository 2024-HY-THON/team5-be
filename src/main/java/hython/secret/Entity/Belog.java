package hython.secret.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Entity
@Getter
@Setter
public class Belog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int belog_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Date datetime;

    @Column(nullable = false)
    private String title;

    @ColumnDefault("false")
    private boolean is_anonymous;
}
