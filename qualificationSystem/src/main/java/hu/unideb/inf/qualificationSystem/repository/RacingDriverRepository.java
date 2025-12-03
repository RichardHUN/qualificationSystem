package hu.unideb.inf.qualificationSystem.repository;

import hu.unideb.inf.qualificationSystem.model.RacingDriver;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for racing driver entities.
 */
@Repository
public interface RacingDriverRepository
        extends CrudRepository<RacingDriver, Integer> {
}
