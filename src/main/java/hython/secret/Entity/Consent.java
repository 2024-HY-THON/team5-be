package hython.secret.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Consent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int consent_id;

    @Column(nullable = false)
    private String consent_name;

    @Column(nullable = false)
    private boolean is_consent;
}
