package hython.secret.Service;

import hython.secret.Entity.*;
import hython.secret.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<String> getAllStickerImages(){
        List<StickerImage> stickers = stickerImageRepository.findAll();
        return stickers.stream()
                .map(sticker -> encodeImageToBase64(sticker.getImageData()))  // Base64로 인코딩
                .collect(Collectors.toList());  // 리스트로 반환
    }

    /**
     * 말풍선 이미지 가져오기
     * */
    public String getSBImage(int id){
        SpeechBubbleImage sb = sbRepository.findById(id);

        return encodeImageToBase64(sb.getImageData());
    }
    public List<String> getAllSBImages(){
        List<SpeechBubbleImage> sbs = sbRepository.findAll();
        return sbs.stream()
                .map(sb -> encodeImageToBase64(sb.getImageData()))  // Base64로 인코딩
                .collect(Collectors.toList());  // 리스트로 반환
    }

    /**
     * 스탬프 이미지 가져오기
     * */
    public String getStampImage(int id){
        StampImage stamp = stampImageRepository.findById(id);

        return encodeImageToBase64(stamp.getImageData());
    }
    public List<String> getAllStampImages(){
        List<StampImage> stamps = stampImageRepository.findAll();
        return stamps.stream()
                .map(stamp -> encodeImageToBase64(stamp.getImageData()))  // Base64로 인코딩
                .collect(Collectors.toList());  // 리스트로 반환
    }

    /**
     * 캐릭터 이미지 가져오기
     * */
    public String getCharacterImg(int id){
        CharacterImage character = characterImgRepository.findById(id);

        return encodeImageToBase64(character.getImageData());
    }
    public List<String> getAllCharacterImages(){
        List<CharacterImage> characters = characterImgRepository.findAll();
        return characters.stream()
                .map(character -> encodeImageToBase64(character.getImageData()))  // Base64로 인코딩
                .collect(Collectors.toList());  // 리스트로 반환
    }


    /**
     * 모자 이미지 가져오기
     * */
    public String getHatImage(int id){
        HatImage hat = hatImageRepository.findById(id);

        return encodeImageToBase64(hat.getImageData());
    }

    public List<String> getAllHatImages(){
        List<HatImage> hats = hatImageRepository.findAll();
        return hats.stream()
                .map(hat -> encodeImageToBase64(hat.getImageData()))  // Base64로 인코딩
                .collect(Collectors.toList());  // 리스트로 반환
    }




    private String encodeImageToBase64(byte[] imageData) {
        return Base64.getEncoder().encodeToString(imageData);  // Base64로 인코딩하여 반환
    }
}
