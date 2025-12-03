package hu.unideb.inf.qualificationSystem.service;

import hu.unideb.inf.qualificationSystem.model.RacingDriver;
import hu.unideb.inf.qualificationSystem.repository.RacingDriverRepository;
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
class RacingDriverServiceImplTest {

    @Mock
    private RacingDriverRepository repository;

    @Mock
    private DomainDeletionService deletionService;

    @InjectMocks
    private RacingDriverServiceImpl racingDriverService;

    @Test
    void create_shouldSaveAndReturnDriver() {
        RacingDriver driver = new RacingDriver();
        when(repository.save(driver)).thenReturn(driver);

        RacingDriver result = racingDriverService.create(driver);

        assertEquals(driver, result);
        verify(repository).save(driver);
    }

    @Test
    void getById_shouldReturnDriverWhenExists() {
        Integer id = 1;
        RacingDriver driver = new RacingDriver();
        when(repository.findById(id)).thenReturn(Optional.of(driver));

        Optional<RacingDriver> result = racingDriverService.getById(id);

        assertTrue(result.isPresent());
        assertEquals(driver, result.get());
    }

    @Test
    void getById_shouldReturnEmptyWhenNotExists() {
        Integer id = 1;
        when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<RacingDriver> result = racingDriverService.getById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void getAll_shouldReturnAllDrivers() {
        List<RacingDriver> drivers = List.of(new RacingDriver(), new RacingDriver());
        when(repository.findAll()).thenReturn(drivers);

        Iterable<RacingDriver> result = racingDriverService.getAll();

        assertEquals(drivers, result);
    }

    @Test
    void getAllByParams_shouldFilterByNameAndTeam() {
        RacingDriver driver1 = RacingDriver.builder().name("Lewis Hamilton").team("Mercedes").build();
        RacingDriver driver2 = RacingDriver.builder().name("Max Verstappen").team("Red Bull").build();
        when(repository.findAll()).thenReturn(List.of(driver1, driver2));

        Iterable<RacingDriver> result = racingDriverService.getAllByParams("lewis", "mercedes");

        assertEquals(1, ((List<RacingDriver>) result).size());
        assertEquals(driver1, ((List<RacingDriver>) result).get(0));
    }

    @Test
    void update_shouldUpdateAndReturnDriverWhenExists() {
        Integer id = 1;
        RacingDriver existing = new RacingDriver();
        RacingDriver updated = new RacingDriver();
        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(any(RacingDriver.class))).thenReturn(updated);

        RacingDriver result = racingDriverService.update(id, updated);

        assertEquals(updated, result);
        verify(repository).save(updated);
        assertEquals(id, updated.getNumber());
    }

    @Test
    void update_shouldThrowExceptionWhenNotExists() {
        Integer id = 1;
        RacingDriver driver = new RacingDriver();
        when(repository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> racingDriverService.update(id, driver));

        assertEquals("RacingDriver not found with id: 1", exception.getMessage());
    }

    @Test
    void delete_shouldCallDeletionService() {
        Integer id = 1;

        racingDriverService.delete(id);

        verify(deletionService).deleteDriver(id);
    }

    @Test
    void existsById_shouldReturnTrueWhenExists() {
        Integer id = 1;
        when(repository.existsById(id)).thenReturn(true);

        boolean result = racingDriverService.existsById(id);

        assertTrue(result);
    }

    @Test
    void existsById_shouldReturnFalseWhenNotExists() {
        Integer id = 1;
        when(repository.existsById(id)).thenReturn(false);

        boolean result = racingDriverService.existsById(id);

        assertFalse(result);
    }
}
