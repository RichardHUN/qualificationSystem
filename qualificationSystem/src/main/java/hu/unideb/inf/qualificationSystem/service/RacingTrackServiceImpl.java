package hu.unideb.inf.qualificationSystem.service;

import hu.unideb.inf.qualificationSystem.model.RacingTrack;
import hu.unideb.inf.qualificationSystem.repository.RacingTrackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class RacingTrackServiceImpl implements RacingTrackService {

    private final RacingTrackRepository repository;
    private final DomainDeletionService deletionService;

    @Override
    public RacingTrack create(RacingTrack track) {
        return repository.save(track);
    }

    @Override
    public Optional<RacingTrack> getById(String id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<RacingTrack> getAll() {
        return repository.findAll();
    }

    @Override
    public RacingTrack update(String id, RacingTrack track) {
        Optional<RacingTrack> existing = repository.findById(id);
        if (existing.isPresent()) {
            track.setCity(id);
            return repository.save(track);
        }
        throw new RuntimeException("RacingTrack not found with city: " + id);
    }

    @Override
    public Iterable<RacingTrack> getAllByParams(String name, String country, String city) {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .filter(track -> (
                        (country == null || track.getCountry().toLowerCase().contains(country.toLowerCase())) &&
                        (name == null || track.getName().toLowerCase().contains(name.toLowerCase())) &&
                        (city == null || track.getCity().toLowerCase().contains(city.toLowerCase()))
                    )
                ).toList();
    }

    @Override
    public void delete(String city) {
        log.info("Deleting racing track with city: {}", city);
        deletionService.deleteTrack(city);
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}

