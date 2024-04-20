package ru.russkikh.springcourse.FirstRestApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.russkikh.springcourse.FirstRestApp.models.Sensor;
import ru.russkikh.springcourse.FirstRestApp.repositories.SensorRepository;
import ru.russkikh.springcourse.FirstRestApp.util.SensorNotFoundExeption;

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
        Optional<Sensor> foundSensor = sensorRepository.findById(id);
        return foundSensor.orElseThrow(SensorNotFoundExeption::new);
    }

    public Optional<Sensor> findByName(String name) {
        return sensorRepository.findByName(name);
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorRepository.save(sensor);
    }

    @Transactional
    public void update(int id, Sensor updatedSensor) {
        updatedSensor.setId(id);
        sensorRepository.save(updatedSensor);
    }

    @Transactional
    public void delete(int id) {
        sensorRepository.deleteById(id);
    }
}
