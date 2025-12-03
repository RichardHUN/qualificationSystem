package hu.unideb.inf.qualificationSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class.
 */
@SpringBootApplication
public class QualificationSystemApplication {

    /**
     * Main method to start the application.
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(
            QualificationSystemApplication.class, args);
    }

}
