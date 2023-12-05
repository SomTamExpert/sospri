package ch.bbw.km.winthi.security;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import java.util.List;


@Configuration
public class OAuth2Config {

    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String githubClientId;

    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String githubClientSecret;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;


   public ClientRegistrationRepository clientRegistrationRepository() {
       return new InMemoryClientRegistrationRepository(getRegistrations());
   }
    public List<ClientRegistration> getRegistrations() {
        return List.of(
                CommonOAuth2Provider.GITHUB.getBuilder("github")
                        .clientId(githubClientId)
                        .clientSecret(githubClientSecret)
                        .build(),
                CommonOAuth2Provider.GOOGLE.getBuilder("google")
                        .clientId(googleClientId)
                        .clientSecret(googleClientSecret)
                        .build()
        );
    }



}
