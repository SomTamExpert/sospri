package ch.bbw.pr.sospri.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity // enables Spring Security’s web security support and provides the Spring MVC integration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void globalSecurityConfiguration(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("{noop}1234").roles("user"); // configure users in memory
        auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles("user", "admin"); // configures admin in memory
    }

    protected void configure(HttpSecurity http) throws Exception { // will call a login form to authenticate users on call http://localhost:8080/
        System.out.println(("Using default configure(HttpSecurity). If subclassed this will potentially override subclass configure(HttpSecurity)."));

        http
            .authorizeRequests()
                .antMatchers("/get-members").hasRole("admin")
                .antMatchers("/get-channel").hasAnyRole("user", "admin")
                .and()
            .formLogin()
                .and()
            .exceptionHandling()
                .accessDeniedPage("/forbidden");
    }


}
