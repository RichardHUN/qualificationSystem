package hu.unideb.inf.qualificationSystem.service;

import hu.unideb.inf.qualificationSystem.model.RacingDriver;
import java.util.Optional;

public interface RacingDriverService {

    RacingDriver create(RacingDriver driver);

    Optional<RacingDriver> getById(Integer id);

    Iterable<RacingDriver> getAll();

    Iterable<RacingDriver> getAllByParams(String name, String team);

    RacingDriver update(Integer id, RacingDriver driver);

    void delete(Integer id);

    boolean existsById(Integer id);
}
