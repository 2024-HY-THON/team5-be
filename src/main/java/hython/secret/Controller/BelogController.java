package hython.secret.Controller;

import java.time.LocalDateTime;
import java.util.*;

import hython.secret.API.ApiResponseDTO;
import hython.secret.DTO.BelogDTO;
import hython.secret.Entity.Belog;
import hython.secret.Service.BelogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/belogs")
public class BelogController {
    private final BelogService belogService;

    @Autowired
    public BelogController(BelogService belogService) {
        this.belogService = belogService;
    }

    public ResponseEntity<ApiResponseDTO<Void>> createBelog(@RequestBody BelogDTO request){
        if(belogService.createBelog(request)){
            return ResponseEntity.ok(new ApiResponseDTO<>("success", "별록이 성공적으로 작성 되었습니다.", null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponseDTO<>("error", "별록 작성 중 서버에 오류가 발생했습니다.", null));
    }

}