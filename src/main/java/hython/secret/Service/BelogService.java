package hython.secret.Service;

import java.util.*;
import hython.secret.Entity.Belog;
import hython.secret.Entity.User;
import hython.secret.Repository.BelogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BelogService {
    private final BelogRepository belogRepository;

    public BelogService(BelogRepository belogRepository) {
        this.belogRepository = belogRepository;
    }

    public Belog createBelog(int userId, String title, String content, List<String> tags, List<String> images, boolean isAnonymous) {
        User user = new User();
        user.setUser_id(userId);


        Belog belog = new Belog();
        belog.setUser(user);
        belog.setContent(content);
        belog.setTitle(title);
        belog.setTags(tags);
        belog.setImages(images);
        belog.setDatetime(LocalDateTime.now());
        belog.set_anonymous(isAnonymous);
        return belogRepository.save(belog);
    }

    public Belog updateBelog(int belogId,String title, String content,List<String> tags, List<String> images, boolean isAnonymous) {
        Belog belog = belogRepository.findById(belogId)
                .orElseThrow(() -> new IllegalArgumentException("Belog ID : " + belogId + "not found"));
        belog.setContent(content);
        belog.setDatetime(LocalDateTime.now());
        belog.set_anonymous(isAnonymous);
        belog.setTitle(title);
        belog.setTags(tags);
        belog.setImages(images);
        return belogRepository.save(belog);
    }

    public void deleteBelog(int belogId) {
        if(!belogRepository.existsById(belogId)) {
            throw new IllegalArgumentException("Belog ID : " + belogId + "not found");
        }
        belogRepository.deleteById(belogId);
    }
}
