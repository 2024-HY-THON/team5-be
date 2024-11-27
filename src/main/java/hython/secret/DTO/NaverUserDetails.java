package hython.secret.DTO;

import hython.secret.Repository.OAuth2UserInfo;

import java.util.Map;

// OAuth2를 통해 로그인한 사용자의 정보를 다루기 위함
public class NaverUserDetails implements OAuth2UserInfo {

    private final Map<String, Object> attributes;
    private final Map<String, Object> response;

    public NaverUserDetails(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.response = (Map<String, Object>) attributes.get("response");
        if (this.response == null) {
            throw new IllegalArgumentException("Invalid response structure from Naver");
        }
    }

    @Override
    public String getProvider() {
        return "Naver";
    }

    @Override
    public String getProviderId() {
        if (response != null && response.containsKey("id")) {
            return response.get("id").toString();
        } else if (attributes.containsKey("id")) {
            return attributes.get("id").toString();
        } else {
            throw new IllegalArgumentException("No provider ID found in Naver response");
        }
    }

    @Override
    public String getName() {
        return response.get("name").toString();
    }

    @Override
    public String getNickname() {
        return response.get("nickname").toString();
    }

    @Override
    public String getEmail() {
        return response.get("email").toString();
    }
}
