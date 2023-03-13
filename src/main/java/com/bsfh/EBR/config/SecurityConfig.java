package com.bsfh.EBR.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName(null);

        return http.authorizeHttpRequests((auths) -> auths
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/subscriptions").authenticated()
                        .requestMatchers("/**").permitAll())
                .csrf().disable().cors()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll())
                .httpBasic(withDefaults())
                .requestCache((cache) -> cache.requestCache(requestCache))
                .build();
    }
}
