package ru.russkikh.springcourse.FirstRestApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.russkikh.springcourse.FirstRestApp.models.Sensor;


@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {

}
