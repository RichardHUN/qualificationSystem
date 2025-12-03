package hu.unideb.inf.qualificationSystem.web;

import hu.unideb.inf.qualificationSystem.model.RacingDriver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller interface for racing driver operations.
 */
@RequestMapping("/api/racing-drivers")
public interface RacingDriverController {

    /**
     * Creates a new racing driver.
     * @param driver the driver to create
     * @return response with created driver
     */
    @PostMapping
    ResponseEntity<RacingDriver> create(
            @RequestBody RacingDriver driver);

    /**
     * Gets a racing driver by ID.
     * @param id the driver ID
     * @return response with driver
     */
    @GetMapping("/{id}")
    ResponseEntity<RacingDriver> getById(@PathVariable Integer id);

    /**
     * Gets all racing drivers.
     * @return response with all drivers
     */
    @GetMapping
    ResponseEntity<?> getAll();

    /**
     * Gets racing drivers by parameters.
     * @param name the driver name
     * @param team the driver team
     * @return response with filtered drivers
     */
    @GetMapping("/search")
    ResponseEntity<?> getAllByParams(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String team
    );

    /**
     * Updates a racing driver.
     * @param id the driver ID
     * @param driver the updated driver data
     * @return response with updated driver
     */
    @PutMapping("/{id}")
    ResponseEntity<RacingDriver> update(
            @PathVariable Integer id,
            @RequestBody RacingDriver driver);

    /**
     * Deletes a racing driver.
     * @param id the driver ID
     * @return response
     */
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Integer id);
}

