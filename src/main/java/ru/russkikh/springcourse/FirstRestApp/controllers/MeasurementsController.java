package ru.russkikh.springcourse.FirstRestApp.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.russkikh.springcourse.FirstRestApp.dto.MeasurementDTO;
import ru.russkikh.springcourse.FirstRestApp.dto.MeasurementsResponse;
import ru.russkikh.springcourse.FirstRestApp.models.Measurements;
import ru.russkikh.springcourse.FirstRestApp.services.MeasurementService;
import ru.russkikh.springcourse.FirstRestApp.util.MeasurementException;
import ru.russkikh.springcourse.FirstRestApp.util.MeasurementValidator;
import ru.russkikh.springcourse.FirstRestApp.util.MeasurmentErrorResponse;

import javax.validation.Valid;
import java.util.stream.Collectors;

import static ru.russkikh.springcourse.FirstRestApp.util.ErrorsUtil.returnErrorsToClient;


@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementService measurementService;
    private final MeasurementValidator measurementValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsController(MeasurementService measurementService,
                                  MeasurementValidator measurementValidator,
                                  ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.measurementValidator = measurementValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                          BindingResult bindingResult) {
        Measurements measurementToAdd = convertToMeasurement(measurementDTO);

        measurementValidator.validate(measurementToAdd, bindingResult);
        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        measurementService.addMeasurement(measurementToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping()
    public MeasurementsResponse getMeasurements() {
        // Обычно список из элементов оборачивается в один объект для пересылки
        return new MeasurementsResponse(measurementService.findAll().stream().map(this::convertToMeasurementDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount() {
        return measurementService.findAll().stream().filter(Measurements::getRaining).count();
    }

    private Measurements convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurements.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurements measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurmentErrorResponse> handleException(MeasurementException e) {
        MeasurmentErrorResponse response = new MeasurmentErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
