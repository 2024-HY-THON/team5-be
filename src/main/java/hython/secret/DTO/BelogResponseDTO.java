package hython.secret.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class BelogResponseDTO {
    private int belogId;
    private String content;
    private LocalDateTime createdAt;
    private List<String> tags;
    private long likeCount;

    // 생성자
    public BelogResponseDTO(int belogId, String content, LocalDateTime createdAt, List<String> tags, long likeCount) {
        this.belogId = belogId;
        this.content = content;
        this.createdAt = createdAt;
        this.tags = tags;
        this.likeCount = likeCount;
    }
}
