package hython.secret.Controller;

import java.time.LocalDateTime;
import java.util.*;

import hython.secret.API.ApiResponseDTO;
import hython.secret.DTO.BelogDTO;
import hython.secret.Entity.Belog;
import hython.secret.Service.BelogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/belogs")
public class BelogController {
    private final BelogService belogService;

    @Autowired
    public BelogController(BelogService belogService) {
        this.belogService = belogService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "별록 작성 성공"),
            @ApiResponse(responseCode = "401", description = "오류")
    })
    @Operation(summary = "별록 작성 api",
            description = "태그와 내용을 전달 받으면 저장합니다.")


    @PostMapping("/create")
    public ResponseEntity<ApiResponseDTO<Integer>> createBelog(@RequestBody BelogDTO request){
        int belogId = belogService.createBelog(request);
        return ResponseEntity.ok(new ApiResponseDTO<>("success", "별록이 성공적으로 작성 되었습니다.", belogId));
    }

    @DeleteMapping("/{belogId}")
    public ResponseEntity<ApiResponseDTO<Integer>> deleteBelog(@PathVariable int belogId){
        try {
            int returnbelogId = belogService.deleteBelog(belogId);
//                if (!belog.getUser().getId().equals(requestingUserId)) {
//                    throw new UnauthorizedException("You do not have permission to delete this Belog.");
//                }  belog를 삭제할 권한이 있는지 확인하는 코드
                return ResponseEntity.ok(new ApiResponseDTO<>("success", "별록 삭제가 완료되었습니다", returnbelogId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(new ApiResponseDTO<>("error", e.getMessage(), null));
        }

    }

    @PutMapping("/{belogId}")
    public ResponseEntity<ApiResponseDTO<Integer>> updateBelog(@PathVariable int belogId, @RequestBody BelogDTO request){
        int returnbelogId = belogService.updateBelog(belogId, request);
        return ResponseEntity.ok(new ApiResponseDTO<>("success", "별록 수정이 완료되었습니다", returnbelogId));

    }

    @PostMapping("/{belogId}/share")
    public ResponseEntity<ApiResponseDTO<String>> shareBelog(@PathVariable int belogId){
        try{
            String shareLink = belogService.shareBelog(belogId);
            return ResponseEntity.ok(new ApiResponseDTO<>("success", "공유 완료입니다!", shareLink));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(new ApiResponseDTO<>("error", e.getMessage(), null));
        }
    }

    @PostMapping("/{belogId}/like")
    public ResponseEntity<ApiResponseDTO<Long>> likeBelog(@PathVariable int belogId){
        long result = belogService.likeBelog(belogId);
        return ResponseEntity.ok(new ApiResponseDTO<>("success", "좋아용 증가!", result));

    }
}
