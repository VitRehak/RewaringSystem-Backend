package cz.morosystem.RewardingSystem.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity
@Configuration
public class SpringSecurityConfig {

    @Autowired
    SuccessLoginHandler successLoginHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http.csrf().disable()
                .oauth2Login().successHandler(successLoginHandler).and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/").and()


                .authorizeHttpRequests().requestMatchers("/").permitAll()

                .anyRequest().authenticated();

        return http.build();
    }
}
