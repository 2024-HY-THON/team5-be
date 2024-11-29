package hython.secret.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class HatImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Lob
    private byte[] imageData;

    public HatImage() {}

    public HatImage(String name, byte[] imageData) {
        this.name = name;
        this.imageData = imageData;
    }
}
