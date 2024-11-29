package hython.secret.Controller;

import hython.secret.API.ApiResponseDTO;
import hython.secret.Service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     * 스티커 이미지 변환
     * */

    @Operation(summary = "스티커 이미지 조회 API", description = """
            스티커 이미지를 조회합니다.
        """)
    @GetMapping("/sticker/{id}")
    public ResponseEntity<ApiResponseDTO<String>> getStickerImage(@PathVariable int id){
        try{
            String baseImage = imageService.getStickerImage(id);
            ApiResponseDTO<String> responseDTO = new ApiResponseDTO<>(
                    "success",
                    "스티커 사진을 성공적으로 조회했습니다.",
                    baseImage,
                    null
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e){
            ApiResponseDTO<String> responseDTO = new ApiResponseDTO<>(
                    "error",
                    "스티커를 찾을 수 없습니다.",
                    null,
                    null
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
        }
    }

    /**
     *  스티커 이미지 전체 조회
     * */
    @Operation(summary = "스티커 이미지 전체 조회 API", description = """
            모든 스티커 이미지를 조회합니다.
        """)
    @GetMapping("/sticker")
    public ResponseEntity<ApiResponseDTO<List<String>>> getAllStickerImages() {
        try {
            List<String> baseImages = imageService.getAllStickerImages();
            ApiResponseDTO<List<String>> responseDTO = new ApiResponseDTO<>(
                    "success",
                    "모든 스티커 이미지를 성공적으로 조회했습니다.",
                    baseImages,
                    null
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseDTO<List<String>> responseDTO = new ApiResponseDTO<>(
                    "error",
                    "스티커 이미지를 조회하는데 실패했습니다.",
                    null,
                    null
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 스탬프 이미지 변환
     * */
    @Operation(summary = "스탬프 이미지 조회 API", description = """
            스탬프 이미지를 조회합니다.
        """)
    @GetMapping("/stamp/{id}")
    public ResponseEntity<ApiResponseDTO<String>> getStampImage(@PathVariable int id){
        try{
            String baseImage = imageService.getStampImage(id);
            ApiResponseDTO<String> responseDTO = new ApiResponseDTO<>(
                    "success",
                    "스탬프 사진을 성공적으로 조회했습니다.",
                    baseImage,
                    null
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e){
            ApiResponseDTO<String> responseDTO = new ApiResponseDTO<>(
                    "error",
                    "스탬프 사진을 찾을 수 없습니다.",
                    null,
                    null
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 스탬프 이미지 전체 조회
     */
    @Operation(summary = "스탬프 이미지 전체 조회 API", description = """
            모든 스탬프 이미지를 조회합니다.
        """)
    @GetMapping("/stamp")
    public ResponseEntity<ApiResponseDTO<List<String>>> getAllStampImages() {
        try {
            List<String> baseImages = imageService.getAllStampImages();
            ApiResponseDTO<List<String>> responseDTO = new ApiResponseDTO<>(
                    "success",
                    "모든 스탬프 이미지를 성공적으로 조회했습니다.",
                    baseImages,
                    null
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseDTO<List<String>> responseDTO = new ApiResponseDTO<>(
                    "error",
                    "스탬프 이미지를 조회하는데 실패했습니다.",
                    null,
                    null
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 캐릭터 이미지 변환
     * */

    @Operation(summary = "캐릭터 이미지 조회 API", description = """
            캐릭터 이미지를 조회합니다.
        """)
    @GetMapping("/character/{id}")
    public ResponseEntity<ApiResponseDTO<String>> getCharacterImage(@PathVariable int id){
        try{
            String baseImage = imageService.getCharacterImg(id);
            ApiResponseDTO<String> responseDTO = new ApiResponseDTO<>(
                    "success",
                    "캐릭터 사진을 성공적으로 조회했습니다.",
                    baseImage,
                    null
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e){
            ApiResponseDTO<String> responseDTO = new ApiResponseDTO<>(
                    "error",
                    "캐릭터를 찾을 수 없습니다.",
                    null,
                    null
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 캐릭터 이미지 전체 조회
     */
    @Operation(summary = "캐릭터 이미지 전체 조회 API", description = """
            모든 캐릭터 이미지를 조회합니다.
        """)
    @GetMapping("/character")
    public ResponseEntity<ApiResponseDTO<List<String>>> getAllCharacterImages() {
        try {
            List<String> baseImages = imageService.getAllCharacterImages();
            ApiResponseDTO<List<String>> responseDTO = new ApiResponseDTO<>(
                    "success",
                    "모든 캐릭터 이미지를 성공적으로 조회했습니다.",
                    baseImages,
                    null
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseDTO<List<String>> responseDTO = new ApiResponseDTO<>(
                    "error",
                    "캐릭터 이미지를 조회하는데 실패했습니다.",
                    null,
                    null
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 말풍선 이미지 변환
     * */

    @Operation(summary = "말풍선 이미지 조회 API", description = """
            말풍선 이미지를 조회합니다.
        """)
    @GetMapping("/speech-bubble/{id}")
    public ResponseEntity<ApiResponseDTO<String>> getSpeechBubbleImage(@PathVariable int id){
        try{
            String baseImage = imageService.getSBImage(id);
            ApiResponseDTO<String> responseDTO = new ApiResponseDTO<>(
                    "success",
                    "말풍선 사진을 성공적으로 조회했습니다.",
                    baseImage,
                    null
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e){
            ApiResponseDTO<String> responseDTO = new ApiResponseDTO<>(
                    "error",
                    "말풍선을 찾을 수 없습니다.",
                    null,
                    null
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 말풍선 이미지 전체 조회
     */
    @Operation(summary = "말풍선 이미지 전체 조회 API", description = """
            모든 말풍선 이미지를 조회합니다.
        """)
    @GetMapping("/speech-bubble")
    public ResponseEntity<ApiResponseDTO<List<String>>> getAllSpeechBubbleImages() {
        try {
            List<String> baseImages = imageService.getAllSBImages();
            ApiResponseDTO<List<String>> responseDTO = new ApiResponseDTO<>(
                    "success",
                    "모든 말풍선 이미지를 성공적으로 조회했습니다.",
                    baseImages,
                    null
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseDTO<List<String>> responseDTO = new ApiResponseDTO<>(
                    "error",
                    "말풍선 이미지를 조회하는데 실패했습니다.",
                    null,
                    null
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 모자 이미지 변환
     * */
    @Operation(summary = "모자 이미지 조회 API", description = """
            모자 이미지를 조회합니다.
        """)
    @GetMapping("/hat/{id}")
    public ResponseEntity<ApiResponseDTO<String>> getHatImage(@PathVariable int id){
        try{
            String baseImage = imageService.getHatImage(id);
            ApiResponseDTO<String> responseDTO = new ApiResponseDTO<>(
                    "success",
                    "모자 사진을 성공적으로 조회했습니다.",
                    baseImage,
                    null
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e){
            ApiResponseDTO<String> responseDTO = new ApiResponseDTO<>(
                    "error",
                    "모자를 찾을 수 없습니다.",
                    null,
                    null
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 모자 이미지 전체 조회
     */
    @Operation(summary = "모자 이미지 전체 조회 API", description = """
            모든 모자 이미지를 조회합니다.
        """)
    @GetMapping("/hat")
    public ResponseEntity<ApiResponseDTO<List<String>>> getAllHatImages() {
        try {
            List<String> baseImages = imageService.getAllHatImages();
            ApiResponseDTO<List<String>> responseDTO = new ApiResponseDTO<>(
                    "success",
                    "모든 모자 이미지를 성공적으로 조회했습니다.",
                    baseImages,
                    null
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseDTO<List<String>> responseDTO = new ApiResponseDTO<>(
                    "error",
                    "모자 이미지를 조회하는데 실패했습니다.",
                    null,
                    null
            );
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
