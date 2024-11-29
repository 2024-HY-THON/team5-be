package hython.secret.API;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseDTO<T> {

    @Schema(description = "응답 상태", example = "success")
    private String status;

    @Schema(description = "응답 메시지", example = "요청이 성공적으로 처리되었습니다.")
    private String message;

    @Schema(description = "추가 데이터", example = "{}")
    private T data1;

    @Schema(description = "추가 데이터", example = "{}")
    private T data2;

    // 생성자 (data가 있는 경우)
    public ApiResponseDTO(String status, String message, T data1, T data2) {
        this.status = status;
        this.message = message;
        this.data1 = data1;
        this.data2 = data2;
    }

}
