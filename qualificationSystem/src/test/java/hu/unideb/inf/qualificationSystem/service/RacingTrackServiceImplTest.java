package hu.unideb.inf.qualificationSystem.service;

import hu.unideb.inf.qualificationSystem.model.RacingTrack;
import hu.unideb.inf.qualificationSystem.repository.RacingTrackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RacingTrackServiceImplTest {

    @Mock
    private RacingTrackRepository repository;

    @Mock
    private DomainDeletionService deletionService;

    @InjectMocks
    private RacingTrackServiceImpl racingTrackService;

    @Test
    void create_shouldSaveAndReturnTrack() {
        RacingTrack track = new RacingTrack();
        when(repository.save(track)).thenReturn(track);

        RacingTrack result = racingTrackService.create(track);

        assertEquals(track, result);
        verify(repository).save(track);
    }

    @Test
    void getById_shouldReturnTrackWhenExists() {
        String id = "Budapest";
        RacingTrack track = new RacingTrack();
        when(repository.findById(id)).thenReturn(Optional.of(track));

        Optional<RacingTrack> result = racingTrackService.getById(id);

        assertTrue(result.isPresent());
        assertEquals(track, result.get());
    }

    @Test
    void getById_shouldReturnEmptyWhenNotExists() {
        String id = "Budapest";
        when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<RacingTrack> result = racingTrackService.getById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void getAll_shouldReturnAllTracks() {
        List<RacingTrack> tracks = List.of(new RacingTrack(), new RacingTrack());
        when(repository.findAll()).thenReturn(tracks);

        Iterable<RacingTrack> result = racingTrackService.getAll();

        assertEquals(tracks, result);
    }

    @Test
    void update_shouldUpdateAndReturnTrackWhenExists() {
        String id = "Budapest";
        RacingTrack existing = new RacingTrack();
        RacingTrack updated = new RacingTrack();
        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(any(RacingTrack.class))).thenReturn(updated);

        RacingTrack result = racingTrackService.update(id, updated);

        assertEquals(updated, result);
        verify(repository).save(updated);
        assertEquals(id, updated.getCity());
    }

    @Test
    void update_shouldThrowExceptionWhenNotExists() {
        String id = "Budapest";
        RacingTrack track = new RacingTrack();
        when(repository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> racingTrackService.update(id, track));

        assertEquals("RacingTrack not found with city: Budapest", exception.getMessage());
    }

    @Test
    void getAllByParams_shouldFilterByNameCountryCity() {
        RacingTrack track1 = RacingTrack.builder().name("Hungaroring").country("Hungary").city("Budapest").build();
        RacingTrack track2 = RacingTrack.builder().name("Monaco").country("Monaco").city("Monaco").build();
        when(repository.findAll()).thenReturn(List.of(track1, track2));

        Iterable<RacingTrack> result = racingTrackService.getAllByParams("hungaro", "hungary", "budapest");

        assertEquals(1, ((List<RacingTrack>) result).size());
        assertEquals(track1, ((List<RacingTrack>) result).get(0));
    }

    @Test
    void delete_shouldCallDeletionService() {
        String city = "Budapest";

        racingTrackService.delete(city);

        verify(deletionService).deleteTrack(city);
    }

    @Test
    void existsById_shouldReturnTrueWhenExists() {
        String id = "Budapest";
        when(repository.existsById(id)).thenReturn(true);

        boolean result = racingTrackService.existsById(id);

        assertTrue(result);
    }

    @Test
    void existsById_shouldReturnFalseWhenNotExists() {
        String id = "Budapest";
        when(repository.existsById(id)).thenReturn(false);

        boolean result = racingTrackService.existsById(id);

        assertFalse(result);
    }
}
