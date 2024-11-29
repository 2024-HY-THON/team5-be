package hython.secret.Service;

import java.util.*;

import hython.secret.DTO.BelogDTO;
import hython.secret.Entity.Belog;
import hython.secret.Entity.Belog_Tags;
import hython.secret.Entity.Tags;
import hython.secret.Entity.User;
import hython.secret.Repository.BelogRepository;
import hython.secret.Repository.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BelogService {
    private final BelogRepository belogRepository;
    private final TagRepository tagRepository;

    @Autowired
    public BelogService(BelogRepository belogRepository, TagRepository tagRepository) {
        this.belogRepository = belogRepository;
        this.tagRepository = tagRepository;
    }

    public Boolean createBelog(BelogDTO request){

        Belog belog = new Belog();

        String content = request.getContent();
        belog.setContent(content);
        belog.setCreate_at(LocalDateTime.now());

        Set<Belog_Tags> belogTags = new HashSet<>();
        for (String tagName : request.getTags()) {
            // Optional을 활용해 태그를 검색하거나 새 태그 생성
            Tags tag = tagRepository.findByName(tagName)
                    .orElseGet(() -> {
                        Tags newTag = new Tags();
                        newTag.setName(tagName);
                        return tagRepository.save(newTag); // 새로운 태그 저장
                    });

            // Belog_Tags 객체 생성 및 관계 설정
            Belog_Tags belogTag = new Belog_Tags();
            belogTag.setBelog(belog);
            belogTag.setTags(tag);

            // Belog_Tags 추가
            belogTags.add(belogTag);
        }
        belog.setBelogTags(belogTags);
        System.out.println("생성완료오?");
        belogRepository.save(belog);

        return true;
    }

    public Boolean updateBelog(int belogId, BelogDTO request){
        Belog belog = belogRepository.findById(belogId);
        belog.setContent(request.getContent());
        belog.setUpdate_at(LocalDateTime.now());
        Set<Belog_Tags> updatedBelogTags = new HashSet<>();
        for (String tagName : request.getTags()) {
            // Optional을 활용해 태그를 검색하거나 새 태그 생성
            Tags tag = tagRepository.findByName(tagName)
                    .orElseGet(() -> {
                        Tags newTag = new Tags();
                        newTag.setName(tagName);
                        return tagRepository.save(newTag); // 새로운 태그 저장
                    });

            // Belog_Tags 객체 생성 및 관계 설정
            Belog_Tags belogTag = new Belog_Tags();
            belogTag.setBelog(belog);
            belogTag.setTags(tag);

            // Belog_Tags 추가
            updatedBelogTags.add(belogTag);
        }
        belog.setBelogTags(updatedBelogTags);
        belogRepository.save(belog);
        return true;
    }

    public boolean deleteBelog(int belogId) {
        Belog belog = belogRepository.findById(belogId);
        if(belog == null) throw new IllegalArgumentException("Belog ID" + belogId + " not found");
        belogRepository.delete(belog);
        return true;
    }

    @Transactional
    public String shareBelog(int belogId) {
        Belog belog = belogRepository.findById(belogId);
        String shareLink = "http://localhost:8080/belogs/share/" + belogId + "-" + UUID.randomUUID();
        belog.setShared(true);
        belog.setShareLink(shareLink);
        belogRepository.save(belog);
        return shareLink;
    }
}
