package ch.bbw.km.winthi.security;

import ch.bbw.km.winthi.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;


@Slf4j
@Configuration
@EnableWebSecurity // enables Spring Security’s web security support and provides the Spring MVC integration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberService memberService;

    @Autowired
    private OAuth2UserService oAuth2UserService;

    @Autowired
    private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @Autowired
    OAuth2Config oAuth2Config;

    @Autowired
    public void globalSecurityConfiguration(AuthenticationManagerBuilder auth) throws Exception {
        try {
            auth.authenticationProvider(daoAuthenticationProvider());
        } catch (Exception e) {
            log.error("Error in globalSecurityConfiguration: " + e.getMessage());
            throw new Exception("Error in globalSecurityConfiguration: " + e.getMessage());

        }
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.memberService);
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    protected void configure(HttpSecurity http) throws Exception { // will call a login form to authenticate users on call http://localhost:8080/
        log.info("Using default configure(HttpSecurity). If subclassed this will potentially override subclass configure(HttpSecurity).");

        http
                .sessionManagement()
                .maximumSessions(30) // allow only one session at a time for a user
                .maxSessionsPreventsLogin(true) // prevent new login when the maximum sessions limit is reached
                .expiredUrl("/login?expired") // redirect to the login page after session expires
                .and()
                .sessionFixation().migrateSession() // prevent session fixation attack
                .and()
                .authorizeRequests()
                .antMatchers("/get-members").hasAuthority("admin")
//                .antMatchers("/get-channel?name=Secret").hasAuthority("admin")
//                .antMatchers("/get-channel**").hasAnyAuthority("member", "admin", "supervisor", "moderator")
                .antMatchers("/add-channel").hasAnyAuthority("admin", "supervisor")
                .antMatchers("/edit-channel**").hasAnyAuthority("admin", "supervisor")
                .antMatchers("/delete-channel**").hasAnyAuthority("admin", "supervisor")
                .antMatchers("/edit-message**").hasAnyAuthority("admin", "moderator")
                .antMatchers("/delete-message").hasAnyAuthority("admin", "moderator")
                .antMatchers("/h2-console/**").permitAll() // this will allow access to h2-console
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(oAuth2UserService)
                .and()
                .clientRegistrationRepository(oAuth2Config.clientRegistrationRepository())
                .authorizedClientService(oAuth2AuthorizedClientService)
                .and()
                .oauth2Client()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/forbidden")
                .and()
                .csrf().ignoringAntMatchers("/h2-console/**") //h2 runs in a frame, which chrome blocks per default, so we need to disable csrf protection by ignoring the h2-console
                .and()
                .headers().frameOptions().sameOrigin();
    }

}
