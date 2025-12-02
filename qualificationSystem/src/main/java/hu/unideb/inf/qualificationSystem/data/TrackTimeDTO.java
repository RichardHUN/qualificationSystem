package hu.unideb.inf.qualificationSystem.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import hu.unideb.inf.qualificationSystem.util.TimeFormatDeserializer;
import hu.unideb.inf.qualificationSystem.util.TimeFormatSerializer;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
public class TrackTimeDTO {

    @JsonSerialize(using = TimeFormatSerializer.class)
    @JsonDeserialize(using = TimeFormatDeserializer.class)
    private Duration time;
    private LocalDateTime recordedAt;
    private int driver;
    private String track;

}
