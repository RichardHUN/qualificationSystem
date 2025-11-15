package hu.unideb.inf.qualificationSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RacingDriver {

    @Id
    @EqualsAndHashCode.Include
    private int number;

    private String name;
    private String team;

}
