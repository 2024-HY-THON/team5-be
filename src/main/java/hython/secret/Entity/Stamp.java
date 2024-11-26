package hython.secret.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
public class Stamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stamp_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "belog_id")
    private Belog belog;

    @Column(nullable = false)
    private Date create_at;
}
