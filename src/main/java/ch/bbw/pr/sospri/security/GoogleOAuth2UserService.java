package ch.bbw.pr.sospri.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GoogleOAuth2UserService extends DefaultOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            Map<String, Object> userAttributes = user.getAttributes();
            String sub = (String) userAttributes.get("sub");
            String name = (String) userAttributes.get("name");
            String email = (String) userAttributes.get("email");
            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");

            return new DefaultOAuth2User(authorities, userAttributes, "sub");
        } catch (Exception e) {
            OAuth2Error oauth2Error = new OAuth2Error(
                    OAuth2ErrorCodes.SERVER_ERROR,
                    "Failed to retrieve user info from Google",
                    e.getMessage());
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), e);
        }
    }
}
