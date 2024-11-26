package hython.secret.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Belog_Tags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private Tags tags;

    @ManyToOne
    @JoinColumn(name = "belog_id", nullable = false)
    private Belog belog;
}
