package ru.russkikh.springcourse.FirstRestApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.russkikh.springcourse.FirstRestApp.models.Sensor;
import ru.russkikh.springcourse.FirstRestApp.repositories.SensorRepository;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    public Sensor findOne(int id) {
        Optional<Sensor> foundPerson = sensorRepository.findById(id);
        return foundPerson.orElse(null);
    }
}
