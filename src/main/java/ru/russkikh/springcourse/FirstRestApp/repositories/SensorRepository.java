package ru.russkikh.springcourse.FirstRestApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.russkikh.springcourse.FirstRestApp.models.Person;
import ru.russkikh.springcourse.FirstRestApp.models.Sensor;

import java.util.Optional;


@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Optional<Sensor> findByName(String name);
}
