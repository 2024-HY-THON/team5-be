package hython.secret;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImageUploader {
    public void uploadImage(int userId, String awardTitle, String imagePath) {
        String url = "jdbc:mysql://localhost:3306/hython";  // DB URL
        String username = "root";  // DB 사용자명
        String password = "0824Hyoun@@";  // DB 비밀번호

        String insertQuery = "INSERT INTO stickerimage (id, name, imageData) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(insertQuery)) {

            // 이미지 파일을 byte[]로 읽기
            File file = new File(imagePath);
            try (FileInputStream fis = new FileInputStream(file)) {
                ps.setInt(1, userId);  // user_id
                ps.setString(2, awardTitle);  // award_title
                ps.setBinaryStream(3, fis, (int) file.length());  // award_image (이미지 파일을 BLOB으로 읽어서 설정)

                ps.executeUpdate();  // 데이터베이스에 삽입
                System.out.println("이미지가 성공적으로 삽입되었습니다.");
            }

        } catch (SQLException | java.io.IOException e) {
            e.printStackTrace();
            System.out.println("이미지 삽입에 실패했습니다.");
        }
    }

    public static void main(String[] args) {
        ImageUploader uploader = new ImageUploader();
        uploader.uploadImage(12, "별3", "C:\\LeeHyunWoo\\secret\\src\\main\\resources\\images\\sticker\\star3.svg");
    }
}
