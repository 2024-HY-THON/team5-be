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
    private int consentId;

    @Column
    private String consentName;

    @Column
    private boolean is_consent;
}
