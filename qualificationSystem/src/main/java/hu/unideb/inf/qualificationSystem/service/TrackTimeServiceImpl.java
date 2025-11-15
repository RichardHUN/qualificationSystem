package hu.unideb.inf.qualificationSystem.service;

import hu.unideb.inf.qualificationSystem.data.TrackTimeDataReader;
import hu.unideb.inf.qualificationSystem.model.RacingDriver;
import hu.unideb.inf.qualificationSystem.model.RacingTrack;
import hu.unideb.inf.qualificationSystem.model.TrackTime;
import hu.unideb.inf.qualificationSystem.repository.TrackTimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackTimeServiceImpl implements TrackTimeService {

    private final TrackTimeRepository repository;
    private final RacingDriverService driverService;
    private final RacingTrackService trackService;
    private final TrackTimeDataReader dataReader;

    @Override
    public Iterable<TrackTime> fill() {
        List<TrackTime> trackTimes = dataReader.readFromJson();

        return trackTimes
                .stream()
                .map(this::create)
                .peek(trackTime -> log.info("Track time inserted: {}", trackTime))
                .toList();
    }

    @Override
    public TrackTime create(TrackTime trackTime) {
        RacingDriver driver = driverService.getById(trackTime.getDriver().getNumber())
                .orElseThrow(() -> new RuntimeException("Driver not found with number: " + trackTime.getDriver().getNumber()));

        RacingTrack track = trackService.getById(trackTime.getTrack().getCity())
                .orElseThrow(() -> new RuntimeException("Track not found with city: " + trackTime.getTrack().getCity()));

        TrackTime toSave = trackTime.toBuilder()
                .recordedAt(LocalDateTime.now())
                .driver(driver)
                .track(track)
                .build();

        return repository.save(toSave);
    }

    @Override
    public Optional<TrackTime> getById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<TrackTime> getAll() {
        return repository.findAllByOrderByTimeAscRecordedAtAsc();
    }

    @Override
    public Iterable<TrackTime> getAllByTrackId(String id) {
        return repository.findAllByTrackCity(id);
    }

    @Override
    public Iterable<TrackTime> getAllByParams(String city, String driverName) {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .filter(time -> city == null || time.getTrack().getCity().toLowerCase().contains(city.toLowerCase()))
                .filter(time -> driverName == null || time.getDriver().getName().toLowerCase().contains(driverName.toLowerCase()))
                .toList();
    }

    @Override
    public TrackTime update(UUID id, TrackTime trackTime) {
        Optional<TrackTime> existing = repository.findById(id);
        if (existing.isPresent()) {
            trackTime.setId(id);
            return repository.save(trackTime);
        }
        throw new RuntimeException("TrackTime not found with id: " + id);
    }

    @Override
    public void delete(UUID id) {
        log.info("Deleting track time with id: {}", id);
        repository.deleteById(id);
    }


    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }
}
