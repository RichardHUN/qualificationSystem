package hu.unideb.inf.qualificationSystem.service;

import hu.unideb.inf.qualificationSystem.model.TrackTime;

import java.util.Optional;
import java.util.UUID;

public interface TrackTimeService {

    Iterable<TrackTime> fill();

    TrackTime create(TrackTime trackTime);

    Optional<TrackTime> getById(UUID id);

    Iterable<TrackTime> getAll();

    Iterable<TrackTime> getAllByTrackId(String id);

    Iterable<TrackTime> getAllByParams(String city, String driverName);

    TrackTime update(UUID id, TrackTime trackTime);

    void delete(UUID id);


    boolean existsById(UUID id);
}

