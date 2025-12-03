package hu.unideb.inf.qualificationSystem.runner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.unideb.inf.qualificationSystem.model.RacingTrack;
import hu.unideb.inf.qualificationSystem.repository.RacingTrackRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

/**
 * Command line runner to load racing track data.
 */
@Slf4j
@Order(2)
@Component
@AllArgsConstructor
public final class RacingTrackRunner implements CommandLineRunner {

    private final RacingTrackRepository repository;

    @Override
    public void run(final String... args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream =
                new ClassPathResource("f1-tracks.json")
                        .getInputStream();

        List<RacingTrack> tracks = mapper.readValue(
                inputStream,
                new TypeReference<List<RacingTrack>>() { });

        tracks.stream()
                .map(repository::save)
                .forEach(track ->
                        log.info("Racing track inserted: {}", track));
    }
}

