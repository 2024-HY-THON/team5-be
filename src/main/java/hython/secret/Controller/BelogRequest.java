package hython.secret.Controller;

import java.util.*;
import lombok.Getter;
import lombok.Setter;


public class BelogRequest {
    private int userId;
    private String title;
    private String content;
    private List<String> tags;
    private List<String> images;
    private boolean isAnonymous;
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public List<String> getImages() {
        return images;
    }
    public void setImages(List<String> images) {
        this.images = images;
    }
    public boolean isAnonymous() {
        return isAnonymous;
    }
    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }
}
