package hu.unideb.inf.qualificationSystem.runner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.unideb.inf.qualificationSystem.model.RacingDriver;
import hu.unideb.inf.qualificationSystem.repository.RacingDriverRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Order(1)
@Component
@AllArgsConstructor
public class RacingDriverRunner implements CommandLineRunner {

    private final RacingDriverRepository repository;

    @Override
    public void run(String... args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = new ClassPathResource("f1-drivers.json").getInputStream();

        List<RacingDriver> drivers = mapper.readValue(inputStream, new TypeReference<List<RacingDriver>>() {});

        drivers.stream()
                .map(repository::save)
                .forEach(driver -> log.info("Racing driver inserted: {}", driver));
    }
}
