package hu.unideb.inf.qualificationSystem.service;

import hu.unideb.inf.qualificationSystem.repository.RacingDriverRepository;
import hu.unideb.inf.qualificationSystem.repository.RacingTrackRepository;
import hu.unideb.inf.qualificationSystem.repository.TrackTimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DomainDeletionService {

    private final TrackTimeRepository trackTimeRepository;
    private final RacingDriverRepository driverRepository;
    private final RacingTrackRepository trackRepository;

    @Transactional
    public void deleteDriver(Integer driverNumber) {
        trackTimeRepository.deleteAllByDriverNumber(driverNumber);
        log.info("Deleted all track times for driver number: {}", driverNumber);

        driverRepository.deleteById(driverNumber);
        log.info("Deleted driver with number: {}", driverNumber);
    }

    @Transactional
    public void deleteTrack(String city) {
        trackTimeRepository.deleteAllByTrackCity(city);
        log.info("Deleted all track times for track city: {}", city);

        trackRepository.deleteById(city);
        log.info("Deleted track with city: {}", city);
    }
}

