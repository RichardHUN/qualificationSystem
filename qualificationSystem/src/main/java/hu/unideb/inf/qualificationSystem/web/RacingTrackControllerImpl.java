package hu.unideb.inf.qualificationSystem.web;

import hu.unideb.inf.qualificationSystem.model.RacingTrack;
import hu.unideb.inf.qualificationSystem.service.RacingTrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RacingTrackControllerImpl implements RacingTrackController {

    private final RacingTrackService service;

    @Override
    public ResponseEntity<RacingTrack> create(RacingTrack track) {
        RacingTrack saved = service.create(track);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Override
    public ResponseEntity<RacingTrack> getById(String city) {
        return service.getById(city)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    public ResponseEntity<?> getAllByParams(String name, String country, String city) {
        return ResponseEntity.ok(service.getAllByParams(name, country, city));
    }

    @Override
    public ResponseEntity<RacingTrack> update(String id, RacingTrack track) {
        try {
            RacingTrack updated = service.update(id, track);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> delete(String city) {
        if (service.existsById(city)) {
            service.delete(city);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

