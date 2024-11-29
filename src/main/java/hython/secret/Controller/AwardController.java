package hython.secret.Controller;

import hython.secret.API.ApiResponseDTO;
import hython.secret.DTO.AwardDTO;
import hython.secret.Service.AwardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/award")
public class AwardController {

    private final AwardService awardService;

    @Autowired
    public AwardController(AwardService awardService) {
        this.awardService = awardService;
    }

    @Operation(summary = "상장 저장 API", description = """
        상장을 저장합니다. DTO로 title, email, awardImage를 전달.
        title : 상장 이름
        email : 사용자 인증을 위한 파라미터
        awardImage :  MultipartFile 형식으로 이미지를 서버에 전송
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상장 저장 성공"),
            @ApiResponse(responseCode = "400", description = "상장 저장 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/saved")
    public ResponseEntity<ApiResponseDTO<String>> savedAward(@RequestBody @Valid AwardDTO request){

        if(awardService.savedImgTitle(request)){
            ApiResponseDTO<String> responseDTO = new ApiResponseDTO<>(
                    "success",
                    "상장 관련 정보를 저장했습니다.",
                    null,
                    null
            );

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        ApiResponseDTO<String> responseDTO = new ApiResponseDTO<>(
                "error",
                "상장 저장에 실패했습니다.",
                null,
                null
        );

        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}
