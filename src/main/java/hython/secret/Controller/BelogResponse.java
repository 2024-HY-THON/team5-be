package hython.secret.Controller;

import hython.secret.Entity.Belog;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class BelogResponse {
    private String message;
    private int belogId;
    private String title;
    private String content;
    private List<String> tags;
    private List<String> images;

    public BelogResponse(String message, Belog belog) {
        this.message = message;
        this.belogId = belog.getBelog_id();
        this.title = belog.getTitle();
        this.content = belog.getContent();
        this.tags = belog.getTags();
        this.images = belog.getImages();
    }
}
