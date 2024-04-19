package ru.russkikh.springcourse.FirstRestApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.russkikh.springcourse.FirstRestApp.models.Measurements;

@Repository
public interface MeasurmentsRepository extends JpaRepository<Measurements, Integer> {
}
