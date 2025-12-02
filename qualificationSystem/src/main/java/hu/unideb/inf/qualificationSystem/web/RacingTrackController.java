package hu.unideb.inf.qualificationSystem.web;

import hu.unideb.inf.qualificationSystem.model.RacingTrack;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/racing-tracks")
public interface RacingTrackController {

    @PostMapping
    ResponseEntity<RacingTrack> create(@RequestBody RacingTrack track);

    @GetMapping("/{city}")
    ResponseEntity<RacingTrack> getById(@PathVariable String city);

    @GetMapping
    ResponseEntity<?> getAll();

    @GetMapping("/search")
    ResponseEntity<?> getAllByParams(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city);

    @PutMapping("/{city}")
    ResponseEntity<RacingTrack> update(
            @PathVariable String city,
            @RequestBody RacingTrack track);

    @DeleteMapping("/{city}")
    ResponseEntity<?> delete(@PathVariable String city);
}

