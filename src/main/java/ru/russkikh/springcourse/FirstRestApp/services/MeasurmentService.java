package ru.russkikh.springcourse.FirstRestApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.russkikh.springcourse.FirstRestApp.models.Measurements;
import ru.russkikh.springcourse.FirstRestApp.repositories.MeasurmentsRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurmentService {

    private final MeasurmentsRepository measurmentsRepository;

    @Autowired
    public MeasurmentService(MeasurmentsRepository measurmentsRepository) {
        this.measurmentsRepository = measurmentsRepository;
    }

    public List<Measurements> findAll() {
        return measurmentsRepository.findAll();
    }

    public Measurements findOne(int id) {
        Optional<Measurements> foundMeasurments = measurmentsRepository.findById(id);
        return foundMeasurments.orElse(null);
    }

}
