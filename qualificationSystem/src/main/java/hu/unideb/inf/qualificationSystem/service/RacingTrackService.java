package hu.unideb.inf.qualificationSystem.service;

import hu.unideb.inf.qualificationSystem.model.RacingTrack;
import java.util.Optional;

/**
 * Service interface for racing track operations.
 */
public interface RacingTrackService {

    /**
     * Creates a new racing track.
     * @param track the track to create
     * @return the created track
     */
    RacingTrack create(RacingTrack track);

    /**
     * Gets a racing track by ID.
     * @param id the track ID
     * @return optional containing the track if found
     */
    Optional<RacingTrack> getById(String id);

    /**
     * Gets all racing tracks.
     * @return all tracks
     */
    Iterable<RacingTrack> getAll();

    /**
     * Gets racing tracks by parameters.
     * @param name the track name
     * @param country the country
     * @param city the city
     * @return filtered tracks
     */
    Iterable<RacingTrack> getAllByParams(
            String name, String country, String city);

    /**
     * Updates a racing track.
     * @param id the track ID
     * @param track the updated track data
     * @return the updated track
     */
    RacingTrack update(String id, RacingTrack track);

    /**
     * Deletes a racing track.
     * @param id the track ID
     */
    void delete(String id);

    /**
     * Checks if a track exists by ID.
     * @param id the track ID
     * @return true if exists
     */
    boolean existsById(String id);
}

