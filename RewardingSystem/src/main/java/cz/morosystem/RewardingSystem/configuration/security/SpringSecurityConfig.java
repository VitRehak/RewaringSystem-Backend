package cz.morosystem.RewardingSystem.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SpringSecurityConfig {

    @Autowired
    SuccessLoginHandler successLoginHandler;
    @Autowired
    LogoutSuccessHandler successLogoutHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//        http.csrf().disable()
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and().csrf().disable()
//                .logout().logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true).deleteCookies("JSESSIONID")
//                .and().oauth2Login().loginPage("/oauth2/authorization/google").successHandler(successLoginHandler);


//        http.csrf().disable()
//                .authorizeRequests().anyRequest()
//                .authenticated()
//                .and().oauth2Login().loginProcessingUrl("/oauth2/authorization/google").successHandler(successLoginHandler)
//                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/loggedout");

        http.csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().oauth2Login().loginProcessingUrl("/auth/login").authorizationEndpoint()
                .baseUri("oauth2/authorization/google");


//        http.csrf().disable()
//                .authorizeRequests().anyRequest().permitAll();


        return http.build();
    }
}
