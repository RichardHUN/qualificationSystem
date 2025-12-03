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

/**
 * Security configuration for the application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures the security filter chain.
     * @param http the HttpSecurity to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            final HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Allow all requests from Angular frontend
                        .requestMatchers(
                            this::isFromAngularFrontend).permitAll()
                        // Require authentication for all requests
                        //.anyRequest().authenticated()
                        //demo
                        .anyRequest().permitAll()
                );
                //demo
                //.httpBasic(Customizer.withDefaults());

        return http.build();
    }

    /**
     * Checks if the request is from the Angular frontend.
     * running on localhost:8084.
     * @param request the HTTP request
     * @return true if from Angular frontend
     */
    private boolean isFromAngularFrontend(
            final HttpServletRequest request) {
        String origin = request.getHeader("Origin");
        String referer = request.getHeader("Referer");

        // Check if Origin header matches frontend URL
        if (origin != null
                && origin.equals("http://localhost:8084")) {
            return true;
        }

        // Check if Referer header starts with frontend URL
        if (referer != null
                && referer.startsWith("http://localhost:8084/")) {
            return true;
        }

        return false;
    }

    /**
     * Configures the user details service.
     * @return the user details service
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    /**
     * Configures the password encoder.
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

