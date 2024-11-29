package hython.secret.Service;

import hython.secret.DTO.AwardDTO;
import hython.secret.Entity.Award;
import hython.secret.Entity.User;
import hython.secret.Repository.AwardRepository;
import hython.secret.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AwardService {

    private static final Logger log = LoggerFactory.getLogger(AwardService.class);
    private final UserRepository userRepository;
    private final AwardRepository awardRepository;

    public AwardService(UserRepository userRepository, AwardRepository awardRepository) {
        this.userRepository = userRepository;
        this.awardRepository = awardRepository;
    }

    public boolean savedImgTitle(AwardDTO request){

        String email = request.getEmail();
        String title = request.getTitle();
        MultipartFile awardImage = request.getAwardImage();
        User user = userRepository.findByEmail(email);

        if (user == null){
            log.warn("유저를 찾을 수 없습니다.");
            return false;
        }

        Award award = new Award();

        award.setUser(user);
        award.setAwardTitle(title);

        try {
            // 이미지 파일을 byte[]로 변환하여 Award 객체에 설정
            byte[] imageBytes = awardImage.getBytes(); // MultipartFile을 byte[]로 변환
            award.setAwardImage(imageBytes); // Award 객체에 이미지 저장
        } catch (IOException e) {
            log.error("이미지 변환 중 오류 발생: {}", e.getMessage());
            return false; // 이미지 변환 오류 시 실패
        }

        log.info("상장 저장이 완료 되었습니다.");
        awardRepository.save(award);

        return true;
    }

}
