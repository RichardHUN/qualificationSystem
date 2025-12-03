package hu.unideb.inf.qualificationSystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a racing track.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RacingTrack {

    private String name;
    private String country;

    @Id
    private String city;

    @JsonProperty("length")
    private Float lengthInKm;

    @JsonProperty("elevation")
    private int elevationInMetersMeasuredFromSeaLevel;

}
