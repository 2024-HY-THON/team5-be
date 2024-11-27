package hython.secret.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Award {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int awardId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String awardTitle;

    @Column
    private String awardContent;
}
