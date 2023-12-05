package ch.bbw.km.winthi.security;

import ch.bbw.km.winthi.member.Member;
import ch.bbw.km.winthi.member.MemberRepository;
import ch.bbw.km.winthi.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    MemberService memberservice;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        System.out.println("user " + user.getName());
        Map<String, Object> attributes = new HashMap<>(user.getAttributes());
        System.out.println("attributes " + attributes);
        attributes.put("sub", user.getName());
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("OAuth2UserService.loadUser(): registrationId: " + registrationId);
        if( memberRepository.findById(Long.valueOf(user.getName())).isEmpty() ) {
            System.out.println("member is empty");
            Member member = new Member();
            member.setAuthority("member");
            member.setUsername(user.getName());
            String username = user.getAttributes().get("login").toString();
            String[] parts = username.split("-");
            String firstName = parts[0]; // "marco"
            String lastName = parts[1];
            member.setPrename(firstName);
            member.setLastname(lastName);

            memberservice.addOauthUser(member);
        }
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("member")),
                attributes,
                "sub" // Use "sub" as the key to look up the user's unique identifier in the attributes map
        );
    }
}
