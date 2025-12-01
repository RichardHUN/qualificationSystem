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
                        // Allow requests from Angular frontend (same origin)
                        .requestMatchers(this::isFromAngularFrontend).permitAll()
                        // Require authentication for all other requests
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    /**
     * Checks if the request is from the Angular frontend by validating:
     * 1. API endpoints (allow /api/* for Angular service calls)
     * 2. Static resources (JS, CSS, HTML, fonts, icons)
     * 3. Navigation routes (paths without file extensions - for Angular routing)
     * 4. Referer header (for navigation within the app)
     * 5. Origin header (for AJAX requests)
     */
    private boolean isFromAngularFrontend(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        String origin = request.getHeader("Origin");
        String requestUri = request.getRequestURI();

        // Allow API endpoints - Angular services may use /api/* or /qualificationSystem/api/*
        if (requestUri.startsWith("/api/") || requestUri.startsWith("/qualificationSystem/api/")) {
            return true;
        }

        // Allow root path and trailing slash
        if (requestUri.equals("/qualificationSystem") || requestUri.equals("/qualificationSystem/")) {
            return true;
        }

        // Allow static resources with file extensions
        if (requestUri.matches(".*\\.(html|js|css|ico|woff|woff2|ttf|svg|png|jpg|jpeg|gif|map)$")) {
            return true;
        }

        // Allow navigation routes (paths without file extensions - for Angular client-side routing)
        // This allows /qualificationSystem/drivers, /qualificationSystem/tracks, etc.
        String path = requestUri.substring(requestUri.lastIndexOf("/") + 1);
        if (!path.contains(".")) {
            return true;
        }

        // Allow requests with valid referer from same origin
        if (referer != null && referer.startsWith("http://localhost:8084/")) {
            return true;
        }

        // Allow requests with valid origin from same origin
        if (origin != null && origin.equals("http://localhost:8084")) {
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