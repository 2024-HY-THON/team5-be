package hython.secret.Service;

import java.util.*;

import hython.secret.DTO.BelogDTO;
import hython.secret.DTO.BelogResponseDTO;
import hython.secret.Entity.*;
import hython.secret.Repository.BelogRepository;
import hython.secret.Repository.TagRepository;
import hython.secret.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class BelogService {
    private final BelogRepository belogRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    @Autowired
    public BelogService(BelogRepository belogRepository, TagRepository tagRepository, UserRepository userRepository) {
        this.belogRepository = belogRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Belog createBelog(BelogDTO request){

        Belog belog = new Belog();
        String email = request.getEmail();
        User user = userRepository.findByEmail(email);
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
        belog.setScope(Scope.ALL);
        belog.setBelogTags(belogTags);
        belog.setUser(user);
        Belog savedBelog = belogRepository.save(belog);
        return savedBelog;
    }

    public Belog updateBelog(int belogId, BelogDTO request){
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
        Belog savedBelog = belogRepository.save(belog);
        return savedBelog;
    }

    public int deleteBelog(int belogId) {
        Belog belog = belogRepository.findById(belogId);
        if(belog == null) throw new IllegalArgumentException("Belog ID" + belogId + " not found");
        int result = belog.getBelogId();
        belogRepository.delete(belog);
        return result;
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

    @Transactional
    public long likeBelog(int belogId) {
        Belog belog = belogRepository.findById(belogId);
        if(belog == null) throw new IllegalArgumentException("Belog ID" + belogId + " not found");
        belog.incrementBelogLikeCount();
        return belog.getBelogLikeCount();
    }

    public List<BelogResponseDTO> getRandomBelogs() {
        // Scope가 ALL인 Belog 중에서 랜덤으로 3개 조회
        List<Belog> belogs = belogRepository.findRandomByScope(Scope.ALL.name(), 3);

        // Belog를 DTO로 변환
        return belogs.stream()
                .map(belog -> new BelogResponseDTO(
                        belog.getBelogId(),
                        belog.getContent(),
                        belog.getCreate_at(),
                        belog.getBelogTags().stream().map(tag -> tag.getTags().getName()).collect(Collectors.toList()),
                        belog.getBelogLikeCount()
                ))
                .collect(Collectors.toList());
    }
}
