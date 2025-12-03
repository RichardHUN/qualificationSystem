package hu.unideb.inf.qualificationSystem.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import hu.unideb.inf.qualificationSystem.util.TimeFormatDeserializer;
import hu.unideb.inf.qualificationSystem.util.TimeFormatSerializer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a track time record.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TrackTime {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JsonSerialize(using = TimeFormatSerializer.class)
    @JsonDeserialize(using = TimeFormatDeserializer.class)
    private Duration time;

    private LocalDateTime recordedAt;

    @ManyToOne
    private RacingDriver driver;

    @ManyToOne
    private RacingTrack track;

}
