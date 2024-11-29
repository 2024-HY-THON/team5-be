package hython.secret.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class SpeechBubbleImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Lob
    private byte[] imageData;

    public SpeechBubbleImage() {}

    public SpeechBubbleImage(String name, byte[] imageData) {
        this.name = name;
        this.imageData = imageData;
    }

}
