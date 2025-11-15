package hu.unideb.inf.qualificationSystem.repository;

import hu.unideb.inf.qualificationSystem.model.RacingTrack;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RacingTrackRepository extends CrudRepository<RacingTrack, String> {
}

