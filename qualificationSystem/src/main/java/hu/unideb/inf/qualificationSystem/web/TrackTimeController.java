package hu.unideb.inf.qualificationSystem.web;

import hu.unideb.inf.qualificationSystem.model.RacingDriver;
import hu.unideb.inf.qualificationSystem.model.TrackTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/track-times")
public interface TrackTimeController {

    @PostMapping("/fill")
    ResponseEntity<?> fill();

    @PostMapping
    ResponseEntity<TrackTime> create(@RequestBody TrackTime trackTime);

    @GetMapping("/{id}")
    ResponseEntity<TrackTime> getById(@PathVariable UUID id);

    @GetMapping
    ResponseEntity<?> getAll();

    @GetMapping("/search")
    ResponseEntity<?> getAllByParams(
            @RequestParam (required = false) String city,
            @RequestParam (required = false) String driverName
    );

    @PutMapping("/{id}")
    ResponseEntity<TrackTime> update(@PathVariable UUID id, @RequestBody TrackTime trackTime);

    @PutMapping("/{id}/penalty")
    ResponseEntity<TrackTime> penalty(@PathVariable UUID id, @RequestBody Integer penalty);

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable UUID id);
}

