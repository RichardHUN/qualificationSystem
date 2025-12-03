package hu.unideb.inf.qualificationSystem.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.unideb.inf.qualificationSystem.model.TrackTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Reader for track time data from JSON file.
 */
@Slf4j
@Component
public final class TrackTimeDataReader {

    /**
     * Reads track time data from JSON file.
     * @return list of track times
     */
    public List<TrackTime> readFromJson() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            InputStream input =
                    new ClassPathResource("track-times.json")
                            .getInputStream();
            return mapper.readValue(input,
                    new TypeReference<List<TrackTime>>() { });
        } catch (IOException e) {
            log.warn("Could not read track-times.json file: {}",
                    e.getMessage());
            return new ArrayList<>();
        }
    }
}

