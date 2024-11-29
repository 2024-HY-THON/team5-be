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
    public ResponseEntity<ApiResponseDTO<Void>> createBelog(@RequestBody BelogDTO request){
        if(belogService.createBelog(request)){
            return ResponseEntity.ok(new ApiResponseDTO<>("success", "별록이 성공적으로 작성 되었습니다.", null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponseDTO<>("error", "별록 작성 중 서버에 오류가 발생했습니다.", null));
    }

    @DeleteMapping("/{belogId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteBelog(@PathVariable int belogId){
        try {
            if(belogService.deleteBelog(belogId)){
//                if (!belog.getUser().getId().equals(requestingUserId)) {
//                    throw new UnauthorizedException("You do not have permission to delete this Belog.");
//                }  belog를 삭제할 권한이 있는지 확인하는 코드
                return ResponseEntity.ok(new ApiResponseDTO<>("success", "별록 삭제가 완료되었습니다", null));
            }
            else {
                return ResponseEntity.status(404).body(new ApiResponseDTO<>("error", "별록 삭제에 실패했습니다.", null));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(new ApiResponseDTO<>("error", e.getMessage(), null));
        }

    }

    @PutMapping("/{belogId}")
    public ResponseEntity<ApiResponseDTO<Void>> updateBelog(@PathVariable int belogId, @RequestBody BelogDTO request){
        boolean result = belogService.updateBelog(belogId, request);
        
        if(result){
            return ResponseEntity.ok(new ApiResponseDTO<>("success", "별록 수정이 완료되었습니다", null));
        }
        else{
            return ResponseEntity.status(404).body(new ApiResponseDTO<>("error", "별록 수정에 실패했습니다.", null));
        }
    }

    @PostMapping("/{belogId}/share")
    public ResponseEntity<ApiResponseDTO<Void>> shareBelog(@PathVariable int belogId){
        try{
            String shareLink = belogService.shareBelog(belogId);
            return ResponseEntity.ok(new ApiResponseDTO<>("success", shareLink, null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(new ApiResponseDTO<>("error", e.getMessage(), null));
        }
    }

    @PostMapping("/{belogId}/like")
    public ResponseEntity<ApiResponseDTO<Void>> likeBelog(@PathVariable int belogId){
        boolean result = belogService.likeBelog(belogId);
        if(result){
            return ResponseEntity.ok(new ApiResponseDTO<>("success", "좋아용!", null));
        }
        else{
            return ResponseEntity.status(404).body(new ApiResponseDTO<>("error", "나올일이 없는 오류", null));
        }
    }
}
