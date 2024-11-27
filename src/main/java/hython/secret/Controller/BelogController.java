package hython.secret.Controller;

import java.time.LocalDateTime;
import java.util.*;

import hython.secret.Entity.Belog;
import hython.secret.Service.BelogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/belogs")
public class BelogController {
    private final BelogService belogService;

    public BelogController(BelogService belogService) {
        this.belogService = belogService;
    }

    @PostMapping
    public ResponseEntity<?> createBelog(
            @RequestBody BelogRequest belogRequest) {
        Belog belog = belogService.createBelog(belogRequest.getUserId(),
                belogRequest.getTitle(),
                belogRequest.getContent(),
                belogRequest.getTags(),
                belogRequest.getImages(),
                belogRequest.isAnonymous()
        );
        return ResponseEntity.ok(Map.of(
                "message", "Belog created!",
                "belog_id", belog.getBelog_id()
        ));
    }
}
