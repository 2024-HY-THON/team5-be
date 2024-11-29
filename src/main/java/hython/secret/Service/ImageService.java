package hython.secret.Service;

import hython.secret.Entity.*;
import hython.secret.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class ImageService {

    private final CharacterImgRepository characterImgRepository;
    private final SBRepository sbRepository;
    private final StampImageRepository stampImageRepository;
    private final StickerImageRepository stickerImageRepository;
    private final HatImageRepository hatImageRepository;

    @Autowired
    public ImageService(CharacterImgRepository characterImgRepository, SBRepository sbRepository,
                        StampImageRepository stampImageRepository, StickerImageRepository stickerImageRepository, HatImageRepository hatImageRepository) {
        this.characterImgRepository = characterImgRepository;
        this.sbRepository = sbRepository;
        this.stampImageRepository = stampImageRepository;
        this.stickerImageRepository = stickerImageRepository;
        this.hatImageRepository = hatImageRepository;
    }

    /**
     * 스티커 이미지 가져오기
     */
    public String getStickerImage(int id){
        StickerImage sticker = stickerImageRepository.findById(id);

        return encodeImageToBase64(sticker.getImageData());
    }

    /**
     * 말풍선 이미지 가져오기
     * */
    public String getSBImage(int id){
        SpeechBubbleImage sb = sbRepository.findById(id);

        return encodeImageToBase64(sb.getImageData());
    }

    /**
     * 스탬프 이미지 가져오기
     * */
    public String getStampImage(int id){
        StampImage stamp = stampImageRepository.findById(id);

        return encodeImageToBase64(stamp.getImageData());
    }

    /**
     * 캐릭터 이미지 가져오기
     * */
    public String getCharacterImg(int id){
        CharacterImage character = characterImgRepository.findById(id);

        return encodeImageToBase64(character.getImageData());
    }
    
    /**
     * 모자 이미지 가져오기
     * */
    public String getHatImage(int id){
        HatImage hat = hatImageRepository.findById(id);

        return encodeImageToBase64(hat.getImageData());
    }




    private String encodeImageToBase64(byte[] imageData) {
        return Base64.getEncoder().encodeToString(imageData);  // Base64로 인코딩하여 반환
    }
}
