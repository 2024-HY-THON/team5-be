package hython.secret.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tag_id;

    @Column(unique=true)
    private String tag_name;
}
