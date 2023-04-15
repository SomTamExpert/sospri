package ch.bbw.pr.sospri.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class OAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        Map<String, Object> attributes = new HashMap<>(user.getAttributes());
        attributes.put("sub", user.getName());
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("OAuth2UserService.loadUser(): registrationId: " + registrationId);
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("member")),
                attributes,
                "sub" // Use "sub" as the key to look up the user's unique identifier in the attributes map
        );
    }
}
