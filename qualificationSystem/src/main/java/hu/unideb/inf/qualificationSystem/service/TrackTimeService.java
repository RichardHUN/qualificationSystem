package hu.unideb.inf.qualificationSystem.service;

import hu.unideb.inf.qualificationSystem.data.TrackTimeDTO;
import hu.unideb.inf.qualificationSystem.model.TrackTime;

import java.util.Optional;
import java.util.UUID;

public interface TrackTimeService {

    Iterable<TrackTime> fill();

    TrackTime create(TrackTime trackTime);

    TrackTime create(TrackTimeDTO trackTime);

    Optional<TrackTime> getById(UUID id);

    Iterable<TrackTime> getAll();

    Iterable<TrackTime> getAllByTrackId(String id);

    Iterable<TrackTime> getAllByParams(String city, String driverName);

    TrackTime update(UUID id, TrackTime trackTime);

    TrackTime updateWithDTO(UUID id, TrackTimeDTO trackTime);

    TrackTime penalty(UUID id, Integer penalty);

    void delete(UUID id);

    boolean existsById(UUID id);
}

