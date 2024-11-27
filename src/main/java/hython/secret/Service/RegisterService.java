package hython.secret.Service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RegisterService {



    /**
     * 유저번호 생성 메서드
     * */
    public static String generateCode(){
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

}
