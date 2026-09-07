package com.example.trainingfullstack.config;


import com.example.trainingfullstack.security.CustomUserDetail;
import com.example.trainingfullstack.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetail userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationProvider authenticationProvider
    ) {
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)

                .formLogin(AbstractHttpConfigurer::disable)

                .httpBasic(AbstractHttpConfigurer::disable)

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth
                        /*
                        * Public Endpoint
                        */
                        .requestMatchers(
                                "/api/v1/users/register",
                                "/api/v1/auth/login",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        /*
                        * Admin Endpoint
                        */
                        .requestMatchers(HttpMethod.GET,"/api/v1/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/{userId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/{userId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/users/{userUuid}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/tasks").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/tasks/{taskUuid}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/tasks").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/tasks/{taskUuid}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/auth/me").hasRole("ADMIN")

                        .anyRequest()
                        .authenticated()
                )

                .authenticationProvider(authenticationProvider())

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .build();
    }

}
