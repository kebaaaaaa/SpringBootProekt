package com.avtopark.avtopark.security;

import com.avtopark.avtopark.entity.User;
import com.avtopark.avtopark.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User userEntity = java.util.Optional.ofNullable(userRepository.findByUsername(username))
                    .orElseThrow(() -> new UsernameNotFoundException(
                            "User not found with username: " + username
                    ));
            String storedPassword = userEntity.getPassword();
            if (storedPassword != null && !storedPassword.startsWith("$2a$")
                    && !storedPassword.startsWith("$2b$")
                    && !storedPassword.startsWith("$2y$")) {
                storedPassword = "{noop}" + storedPassword;
            }
            return org.springframework.security.core.userdetails.User.withUsername(userEntity.getUsername())
                    .password(storedPassword)
                    .authorities(getAuthorities(userEntity))
                    .accountExpired(!userEntity.isAccountNonExpired())
                    .accountLocked(!userEntity.isAccountNonLocked())
                    .credentialsExpired(!userEntity.isCredentialsNonExpired())
                    .disabled(!userEntity.isEnabled())
                    .build();
        };
    }
    
    private org.springframework.security.core.GrantedAuthority[] getAuthorities(User userEntity) {
        return new org.springframework.security.core.GrantedAuthority[]{
                new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + userEntity.getRole().getName().toUpperCase())
        };
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login.html").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/auth/me").permitAll()
                .requestMatchers(HttpMethod.POST, "/perform_login").permitAll()
                .requestMatchers(HttpMethod.GET, "/users.html").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/cars").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/cars/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/cars").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/cars/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/cars/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/car-details").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/car-details/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/car-details").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/car-details/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/car-details/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/rentals").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/api/rentals/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/api/rentals").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/api/rentals/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, "/api/rentals/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/services").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/services/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/services").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/services/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/services/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/car-images").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/car-images/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/car-images").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/car-images/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/car-images/**").hasRole("ADMIN")
                .requestMatchers("/api/auth/**").authenticated()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login.html")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/index.html", true)
                .failureUrl("/login.html?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/perform_logout")
                .logoutSuccessUrl("/login.html?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));
        
        return http.build();
    }
}