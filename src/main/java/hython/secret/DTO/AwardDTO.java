package hython.secret.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class AwardDTO {

    private String title;
    private String email;
    private MultipartFile awardImage;
}
