package hu.unideb.inf.qualificationSystem.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Allow all requests from Angular frontend (localhost:8084)
                        .requestMatchers(this::isFromAngularFrontend).permitAll()
                        // Require authentication for all other requests
                        //.anyRequest().authenticated()
                        //demo
                        .anyRequest().permitAll()
                )
                //demo
                ;//.httpBasic(Customizer.withDefaults());

        return http.build();
    }

    /**
     * Checks if the request is from the Angular frontend running on localhost:8084
     */
    private boolean isFromAngularFrontend(HttpServletRequest request) {
        String origin = request.getHeader("Origin");
        String referer = request.getHeader("Referer");

        // Check if Origin header matches frontend URL
        if (origin != null && origin.equals("http://localhost:8084")) {
            return true;
        }

        // Check if Referer header starts with frontend URL
        if (referer != null && referer.startsWith("http://localhost:8084/")) {
            return true;
        }

        return false;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}