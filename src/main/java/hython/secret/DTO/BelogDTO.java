package hython.secret.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BelogDTO {

    private String content;
    private Set<String> tags;
    private String email;
}
