package hython.secret.Service;

import java.util.*;

import hython.secret.DTO.BelogDTO;
import hython.secret.Entity.Belog;
import hython.secret.Entity.Belog_Tags;
import hython.secret.Entity.Tags;
import hython.secret.Entity.User;
import hython.secret.Repository.BelogRepository;
import hython.secret.Repository.TagRepository;
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
            Tags tag = tagRepository.findByName(tagName);
            tag.setName(tagName);
            tagRepository.save(tag);  // 새로운 태그라면 태그 생성

            Belog_Tags belogTag = new Belog_Tags();
            belogTag.setBelog(belog);
            belogTag.setTags(tag);

            belogTags.add(belogTag);
        }
        belog.setBelogTags(belogTags);

        belogRepository.save(belog);

        return true;
    }

    public Boolean updateBelog(int belogId, BelogDTO request){
        Belog belog = belogRepository.findById(belogId);
        belog.setContent(request.getContent());
        belog.setUpdate_at(LocalDateTime.now());
        Set<Belog_Tags> updatedBelogTags = new HashSet<>();
        for (String tagName : request.getTags()) {
            Tags tag = tagRepository.findByName(tagName);
            tag.setName(tagName);
            tagRepository.save(tag);
            Belog_Tags belogTag = new Belog_Tags();
            belogTag.setBelog(belog);
            belogTag.setTags(tag);
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
}
