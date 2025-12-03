package hu.unideb.inf.qualificationSystem.repository;

import hu.unideb.inf.qualificationSystem.model.TrackTime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository for track time entities.
 */
@Repository
public interface TrackTimeRepository
        extends CrudRepository<TrackTime, UUID> {

    /**
     * Finds all track times ordered by time ascending.
     * @return iterable of track times
     */
    Iterable<TrackTime> findAllByOrderByTimeAsc();

    /**
     * Finds all track times ordered by time and recorded at.
     * @return iterable of track times
     */
    Iterable<TrackTime> findAllByOrderByTimeAscRecordedAtAsc();

    /**
     * Finds all track times by track city.
     * @param city the city name
     * @return iterable of track times
     */
    Iterable<TrackTime> findAllByTrackCity(String city);

    /**
     * Deletes all track times by driver number.
     * @param driverNumber the driver number
     */
    void deleteAllByDriverNumber(Integer driverNumber);

    /**
     * Deletes all track times by track city.
     * @param city the city name
     */
    void deleteAllByTrackCity(String city);
}
