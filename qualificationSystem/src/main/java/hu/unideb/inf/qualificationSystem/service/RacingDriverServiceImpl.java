package hu.unideb.inf.qualificationSystem.service;

import hu.unideb.inf.qualificationSystem.model.RacingDriver;
import hu.unideb.inf.qualificationSystem.repository.RacingDriverRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public final class RacingDriverServiceImpl
        implements RacingDriverService {

    private final RacingDriverRepository repository;
    private final DomainDeletionService deletionService;

    @Override
    public RacingDriver create(RacingDriver driver) {
        return repository.save(driver);
    }

    @Override
    public Optional<RacingDriver> getById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<RacingDriver> getAll() {
        return repository.findAll();
    }

    @Override
    public Iterable<RacingDriver> getAllByParams(String name, String team) {
        return StreamSupport.stream(
                repository.findAll().spliterator(), false)
                .filter(driver -> name == null
                        || driver.getName().toLowerCase()
                                .contains(name.toLowerCase()))
                .filter(driver -> team == null
                        || driver.getTeam().toLowerCase()
                                .contains(team.toLowerCase()))
                .toList();
    }

    @Override
    public RacingDriver update(Integer id, RacingDriver driver) {
        Optional<RacingDriver> existing = repository.findById(id);
        if (existing.isPresent()) {
            driver.setNumber(id);
            return repository.save(driver);
        }
        throw new RuntimeException("RacingDriver not found with id: " + id);
    }

    @Override
    public void delete(Integer id) {
        log.info("Deleting racing driver with id: {}", id);
        deletionService.deleteDriver(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }
}
