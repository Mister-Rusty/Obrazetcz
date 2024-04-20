package ru.russkikh.springcourse.FirstRestApp.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.russkikh.springcourse.FirstRestApp.dto.MeasurmentDTO;
import ru.russkikh.springcourse.FirstRestApp.models.Measurements;
import ru.russkikh.springcourse.FirstRestApp.services.MeasurementService;
import ru.russkikh.springcourse.FirstRestApp.util.MeasurmentErrorResponse;
import ru.russkikh.springcourse.FirstRestApp.util.MeasurmentNotCreatedExeption;
import ru.russkikh.springcourse.FirstRestApp.util.MeasurmentNotFoundExeption;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/measurements")
public class MeasurementsController {

    private final ModelMapper modelMapper;

    private final MeasurementService measurementService;

    @Autowired
    public MeasurementsController(ModelMapper modelMapper, MeasurementService measurementService) {
        this.modelMapper = modelMapper;
        this.measurementService = measurementService;
    }

    @GetMapping()
    @ResponseBody
    public List<MeasurmentDTO> getMeasurments() {
        return measurementService.findAll().stream().map(this::convertToMeasurmentsDTO)
                .collect(Collectors.toList()); // Jackson конвертирует эти объекты в JSON
    }

    @GetMapping("/{id}")
    public MeasurmentDTO getMeasurment(@PathVariable("id") int id) {
        return convertToMeasurmentsDTO(measurementService.findOne(id));
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurmentDTO measurmentDTO,
                                             BindingResult bindingResult) {
        Measurements measurementToAdd = convertToMeasurment(measurmentDTO);
        if (bindingResult.hasErrors()) {

        }
        measurementService.addMeasurement(measurementToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurmentErrorResponse> handleExeption(MeasurmentNotFoundExeption e) {
        MeasurmentErrorResponse response = new MeasurmentErrorResponse("Измерение не найдено в БД", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurmentErrorResponse> handleExeption(MeasurmentNotCreatedExeption e) {
        MeasurmentErrorResponse response = new MeasurmentErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    private MeasurmentDTO convertToMeasurmentsDTO(Measurements measurements) {
        return modelMapper.map(measurements,MeasurmentDTO.class);
    }

    private Measurements convertToMeasurment(MeasurmentDTO measurmentDTO) {
        return modelMapper.map(measurmentDTO,Measurements.class);
    }

}
