package hu.unideb.inf.qualificationSystem.web;

import hu.unideb.inf.qualificationSystem.model.RacingDriver;
import hu.unideb.inf.qualificationSystem.service.RacingDriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public final class RacingDriverControllerImpl
        implements RacingDriverController {

    private final RacingDriverService service;

    @Override
    public ResponseEntity<RacingDriver> create(RacingDriver driver) {
        RacingDriver saved = service.create(driver);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Override
    public ResponseEntity<RacingDriver> getById(Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.ok().build());
    }

    @Override
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    public ResponseEntity<?> getAllByParams(String name, String team) {
        return ResponseEntity.ok(service.getAllByParams(name, team));
    }

    @Override
    public ResponseEntity<RacingDriver> update(
            Integer id, RacingDriver driver) {
        try {
            RacingDriver updated = service.update(id, driver);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
        if (service.existsById(id)) {
            service.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

