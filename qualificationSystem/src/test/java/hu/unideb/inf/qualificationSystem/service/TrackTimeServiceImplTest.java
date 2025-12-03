package hu.unideb.inf.qualificationSystem.service;

import hu.unideb.inf.qualificationSystem.data.TrackTimeDTO;
import hu.unideb.inf.qualificationSystem.data.TrackTimeDataReader;
import hu.unideb.inf.qualificationSystem.model.RacingDriver;
import hu.unideb.inf.qualificationSystem.model.RacingTrack;
import hu.unideb.inf.qualificationSystem.model.TrackTime;
import hu.unideb.inf.qualificationSystem.repository.RacingDriverRepository;
import hu.unideb.inf.qualificationSystem.repository.RacingTrackRepository;
import hu.unideb.inf.qualificationSystem.repository.TrackTimeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrackTimeServiceImplTest {

    @Mock
    private RacingDriverRepository driverRepository;

    @Mock
    private RacingTrackRepository trackRepository;

    @Mock
    private TrackTimeRepository repository;

    @Mock
    private RacingDriverService driverService;

    @Mock
    private RacingTrackService trackService;

    @Mock
    private TrackTimeDataReader dataReader;

    @Spy
    @InjectMocks
    private TrackTimeServiceImpl trackTimeService;

    @Test
    void fill_shouldReadAndCreateTrackTimes() {
        TrackTime trackTime = new TrackTime();
        List<TrackTime> trackTimes = List.of(trackTime);
        when(dataReader.readFromJson()).thenReturn(trackTimes);
        doReturn(trackTime).when(trackTimeService).create(any(TrackTime.class));

        Iterable<TrackTime> result = trackTimeService.fill();

        assertEquals(trackTimes, result);
        verify(dataReader).readFromJson();
        verify(trackTimeService).create(trackTime);
    }

    @Test
    void create_withTrackTime_shouldSaveWhenDriverAndTrackExist() {
        RacingDriver driver = new RacingDriver();
        RacingTrack track = new RacingTrack();
        TrackTime trackTime = TrackTime.builder().driver(driver).track(track).build();
        when(driverService.getById(driver.getNumber())).thenReturn(Optional.of(driver));
        when(trackService.getById(track.getCity())).thenReturn(Optional.of(track));
        when(repository.save(any(TrackTime.class))).thenReturn(trackTime);

        TrackTime result = trackTimeService.create(trackTime);

        assertEquals(trackTime, result);
        verify(repository).save(any(TrackTime.class));
    }

    @Test
    void create_withTrackTime_shouldThrowWhenDriverNotFound() {
        RacingDriver driver = RacingDriver.builder().number(1).build();
        RacingTrack track = new RacingTrack();
        TrackTime trackTime = TrackTime.builder().driver(driver).track(track).build();
        when(driverService.getById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> trackTimeService.create(trackTime));

        assertEquals("Driver not found with number: 1", exception.getMessage());
    }

    @Test
    void create_withTrackTimeDTO_shouldSaveWhenValid() {
        TrackTimeDTO dto = mock(TrackTimeDTO.class);
        when(dto.getDriver()).thenReturn(1);
        when(dto.getTrack()).thenReturn("Budapest");
        when(dto.getTime()).thenReturn(Duration.ofSeconds(100));
        RacingDriver driver = new RacingDriver();
        RacingTrack track = new RacingTrack();
        TrackTime saved = new TrackTime();
        when(driverService.getById(1)).thenReturn(Optional.of(driver));
        when(trackService.getById("Budapest")).thenReturn(Optional.of(track));
        when(repository.save(any(TrackTime.class))).thenReturn(saved);

        TrackTime result = trackTimeService.create(dto);

        assertEquals(saved, result);
        verify(repository).save(any(TrackTime.class));
    }

    @Test
    void getById_shouldReturnTrackTimeWhenExists() {
        UUID id = UUID.randomUUID();
        TrackTime trackTime = new TrackTime();
        when(repository.findById(id)).thenReturn(Optional.of(trackTime));

        Optional<TrackTime> result = trackTimeService.getById(id);

        assertTrue(result.isPresent());
        assertEquals(trackTime, result.get());
    }

    @Test
    void getAll_shouldReturnAllOrdered() {
        List<TrackTime> trackTimes = List.of(new TrackTime());
        when(repository.findAllByOrderByTimeAscRecordedAtAsc()).thenReturn(trackTimes);

        Iterable<TrackTime> result = trackTimeService.getAll();

        assertEquals(trackTimes, result);
    }

    @Test
    void getAllByTrackId_shouldReturnByTrack() {
        String id = "Budapest";
        List<TrackTime> trackTimes = List.of(new TrackTime());
        when(repository.findAllByTrackCity(id)).thenReturn(trackTimes);

        Iterable<TrackTime> result = trackTimeService.getAllByTrackId(id);

        assertEquals(trackTimes, result);
    }

    @Test
    void getAllByParams_shouldFilterByCityAndDriverName() {
        TrackTime time1 = TrackTime.builder().track(RacingTrack.builder().city("Budapest").build()).driver(RacingDriver.builder().name("Lewis").build()).build();
        TrackTime time2 = TrackTime.builder().track(RacingTrack.builder().city("Monaco").build()).driver(RacingDriver.builder().name("Max").build()).build();
        when(repository.findAll()).thenReturn(List.of(time1, time2));

        Iterable<TrackTime> result = trackTimeService.getAllByParams("budapest", "lewis");

        assertEquals(1, ((List<TrackTime>) result).size());
        assertEquals(time1, ((List<TrackTime>) result).get(0));
    }

    @Test
    void update_shouldUpdateWhenExists() {
        UUID id = UUID.randomUUID();
        TrackTime existing = new TrackTime();
        TrackTime updated = new TrackTime();
        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(updated)).thenReturn(updated);

        TrackTime result = trackTimeService.update(id, updated);

        assertEquals(updated, result);
        assertEquals(id, updated.getId());
    }

    @Test
    void update_shouldThrowWhenNotExists() {
        UUID id = UUID.randomUUID();
        TrackTime trackTime = new TrackTime();
        when(repository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> trackTimeService.update(id, trackTime));

        assertEquals("TrackTime not found with id: " + id, exception.getMessage());
    }

    @Test
    void updateWithDTO_shouldUpdateWhenExists() {
        UUID id = UUID.randomUUID();
        TrackTime existing = TrackTime.builder().driver(new RacingDriver()).track(new RacingTrack()).build();
        TrackTimeDTO dto = mock(TrackTimeDTO.class);
        when(dto.getDriver()).thenReturn(1);
        when(dto.getTrack()).thenReturn("Budapest");
        when(dto.getTime()).thenReturn(Duration.ofSeconds(100));
        when(dto.getRecordedAt()).thenReturn(LocalDateTime.now());
        RacingDriver driver = new RacingDriver();
        RacingTrack track = new RacingTrack();
        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(driverRepository.findById(1)).thenReturn(Optional.of(driver));
        when(trackRepository.findById("Budapest")).thenReturn(Optional.of(track));
        when(repository.save(existing)).thenReturn(existing);

        TrackTime result = trackTimeService.updateWithDTO(id, dto);

        assertEquals(existing, result);
        verify(repository).save(existing);
    }

    @Test
    void penalty_shouldAddPenaltyToTime() {
        UUID id = UUID.randomUUID();
        TrackTime existing = TrackTime.builder().time(Duration.ofSeconds(100)).build();
        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        TrackTime result = trackTimeService.penalty(id, 10);

        assertEquals(Duration.ofSeconds(110), result.getTime());
        verify(repository).save(existing);
    }

    @Test
    void penalty_shouldThrowWhenNotExists() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> trackTimeService.penalty(id, 10));

        assertEquals("TrackTime not found with id: " + id, exception.getMessage());
    }

    @Test
    void delete_shouldDeleteById() {
        UUID id = UUID.randomUUID();

        trackTimeService.delete(id);

        verify(repository).deleteById(id);
    }
}
