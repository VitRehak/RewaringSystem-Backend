package cz.morosystem.RewardingSystem.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity
@Configuration
public class SpringSecurityConfig {

    final
    SuccessLoginHandler successLoginHandler;

    public SpringSecurityConfig(SuccessLoginHandler successLoginHandler) {
        this.successLoginHandler = successLoginHandler;
    }

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
