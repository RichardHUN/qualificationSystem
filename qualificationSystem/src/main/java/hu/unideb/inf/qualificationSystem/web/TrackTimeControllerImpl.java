package hu.unideb.inf.qualificationSystem.web;

import hu.unideb.inf.qualificationSystem.data.TrackTimeDTO;
import hu.unideb.inf.qualificationSystem.model.TrackTime;
import hu.unideb.inf.qualificationSystem.service.TrackTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public final class TrackTimeControllerImpl
        implements TrackTimeController {

    private final TrackTimeService service;

    @Override
    public ResponseEntity<?> fill() {
        return ResponseEntity.ok(service.fill());
    }

    @Override
    public ResponseEntity<TrackTime> create(TrackTime trackTime) {
        TrackTime saved = service.create(trackTime);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Override
    public ResponseEntity<TrackTime> create(TrackTimeDTO trackTime) {
        TrackTime saved = service.create(trackTime);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Override
    public ResponseEntity<TrackTime> getById(UUID id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    public ResponseEntity<?> getAllByParams(String city, String driverName) {
        return ResponseEntity.ok(service.getAllByParams(city, driverName));
    }

    @Override
    public ResponseEntity<TrackTime> update(UUID id, TrackTime trackTime) {
        try {
            TrackTime updated = service.update(id, trackTime);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<TrackTime> updateWithDTO(UUID id, TrackTimeDTO trackTime) {
        try {
            TrackTime updated = service.updateWithDTO(id, trackTime);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<TrackTime> penalty(UUID id, Integer penalty) {
        return ResponseEntity.ok(service.penalty(id, penalty));
    }

    @Override
    public ResponseEntity<?> delete(UUID id) {
        if (service.existsById(id)) {
            service.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

