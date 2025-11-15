package hu.unideb.inf.qualificationSystem.web;

import hu.unideb.inf.qualificationSystem.model.RacingDriver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/racing-drivers")
public interface RacingDriverController {

    @PostMapping
    ResponseEntity<RacingDriver> create(@RequestBody RacingDriver driver);

    @GetMapping("/{id}")
    ResponseEntity<RacingDriver> getById(@PathVariable Integer id);

    @GetMapping
    ResponseEntity<?> getAll();

    @GetMapping("/search")
    ResponseEntity<?> getAllByParams(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String team
    );

    @PutMapping("/{id}")
    ResponseEntity<RacingDriver> update(@PathVariable Integer id, @RequestBody RacingDriver driver);

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Integer id);
}

