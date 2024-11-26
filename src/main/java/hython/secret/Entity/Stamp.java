package hython.secret.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime create_at;
}
