package hython.secret.Repository;

public interface OAuth2UserInfo {

    String getProvider();
    String getProviderId();
    String getName();
    String getNickname();
    String getEmail();

}
