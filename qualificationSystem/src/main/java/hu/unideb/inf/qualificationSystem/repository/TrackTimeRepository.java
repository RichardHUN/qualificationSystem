package hu.unideb.inf.qualificationSystem.repository;

import hu.unideb.inf.qualificationSystem.model.TrackTime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TrackTimeRepository extends CrudRepository<TrackTime, UUID> {
    Iterable<TrackTime> findAllByOrderByTimeAsc();

    Iterable<TrackTime> findAllByOrderByTimeAscRecordedAtAsc();

    Iterable<TrackTime> findAllByTrackCity(String city);

    void deleteAllByDriverNumber(Integer driverNumber);

    void deleteAllByTrackCity(String city);
}
