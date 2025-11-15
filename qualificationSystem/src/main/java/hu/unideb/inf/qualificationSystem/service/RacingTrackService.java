package hu.unideb.inf.qualificationSystem.service;

import hu.unideb.inf.qualificationSystem.model.RacingTrack;
import java.util.Optional;

public interface RacingTrackService {

    RacingTrack create(RacingTrack track);

    Optional<RacingTrack> getById(String id);

    Iterable<RacingTrack> getAll();

    Iterable<RacingTrack> getAllByParams(String name, String country, String city);

    RacingTrack update(String id, RacingTrack track);

    void delete(String id);

    boolean existsById(String id);
}

