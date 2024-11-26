package hython.secret.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int image_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String imagePath;
}
