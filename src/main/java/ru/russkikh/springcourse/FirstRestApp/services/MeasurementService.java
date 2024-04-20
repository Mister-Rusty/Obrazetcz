package ru.russkikh.springcourse.FirstRestApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.russkikh.springcourse.FirstRestApp.models.Measurements;
import ru.russkikh.springcourse.FirstRestApp.repositories.MeasurmentsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurmentsRepository measurmentsRepository;
    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurmentsRepository measurmentsRepository, SensorService sensorService) {
        this.measurmentsRepository = measurmentsRepository;
        this.sensorService = sensorService;
    }

    public List<Measurements> findAll() {
        return measurmentsRepository.findAll();
    }

    public Measurements findOne(int id) {
        Optional<Measurements> foundMeasurments = measurmentsRepository.findById(id);
        return foundMeasurments.orElse(null);
    }

    @Transactional
    public void addMeasurement(Measurements measurement) {
        enrichMeasurement(measurement);
        measurmentsRepository.save(measurement);
    }

    public void enrichMeasurement(Measurements measurement) {
        // мы должны сами найти сенсор из БД по имени и вставить объект из Hibernate persistence context'а
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()).get());
        measurement.setCreatedAt(LocalDateTime.now());
    }

}
