package com.example.robinhood.config;

import com.example.robinhood.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;


@Configuration
public class ApplicationSecurityConfig  {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserServiceImpl userService;


    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return new ProviderManager(authProvider);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/user/register");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authenticationManager(authenticationManager())
            .authorizeHttpRequests((authRobinhood) -> { authRobinhood
                .requestMatchers("/user/getData").hasRole("ADMIN")
                .requestMatchers(antMatcher(HttpMethod.GET, "/topic/**/**")).hasAnyRole("ADMIN", "USER")
                .requestMatchers(antMatcher(HttpMethod.GET, "/topic/**")).hasAnyRole("ADMIN", "USER")
                .requestMatchers(antMatcher(HttpMethod.POST, "/topic/**")).hasAnyRole("ADMIN", "USER")
                .requestMatchers(antMatcher(HttpMethod.GET, "/comment/**")).hasAnyRole("ADMIN", "USER")
                .requestMatchers(antMatcher(HttpMethod.POST, "/comment/**")).hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated();
                }
            )
            .formLogin(withDefaults())
            .httpBasic(withDefaults());

        http.csrf((csrf) -> csrf
            .ignoringRequestMatchers("/topic/**")
            .ignoringRequestMatchers("/comment/**")
        );

        return http.build();
    }

}
