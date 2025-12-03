package hu.unideb.inf.qualificationSystem.service;

import hu.unideb.inf.qualificationSystem.repository.RacingDriverRepository;
import hu.unideb.inf.qualificationSystem.repository.RacingTrackRepository;
import hu.unideb.inf.qualificationSystem.repository.TrackTimeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DomainDeletionServiceTest {

    @Mock
    private TrackTimeRepository trackTimeRepository;

    @Mock
    private RacingDriverRepository driverRepository;

    @Mock
    private RacingTrackRepository trackRepository;

    @InjectMocks
    private DomainDeletionService domainDeletionService;

    @Test
    void deleteDriver_shouldDeleteTrackTimesAndDriver() {
        Integer driverNumber = 1;

        domainDeletionService.deleteDriver(driverNumber);

        verify(trackTimeRepository).deleteAllByDriverNumber(driverNumber);
        verify(driverRepository).deleteById(driverNumber);
    }

    @Test
    void deleteTrack_shouldDeleteTrackTimesAndTrack() {
        String city = "Budapest";

        domainDeletionService.deleteTrack(city);

        verify(trackTimeRepository).deleteAllByTrackCity(city);
        verify(trackRepository).deleteById(city);
    }
}
