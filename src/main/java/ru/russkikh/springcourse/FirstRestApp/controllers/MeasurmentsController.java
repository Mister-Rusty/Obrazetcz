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
import ru.russkikh.springcourse.FirstRestApp.services.MeasurmentService;
import ru.russkikh.springcourse.FirstRestApp.util.MeasurmentErrorResponse;
import ru.russkikh.springcourse.FirstRestApp.util.MeasurmentNotCreatedExeption;
import ru.russkikh.springcourse.FirstRestApp.util.MeasurmentNotFoundExeption;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/measurments")
public class MeasurmentsController {

    private ModelMapper modelMapper;

    private MeasurmentService measurmentService;

    @Autowired
    public MeasurmentsController(ModelMapper modelMapper, MeasurmentService measurmentService) {
        this.modelMapper = modelMapper;
        this.measurmentService = measurmentService;
    }

    @GetMapping()
    public List<MeasurmentDTO> getMeasurments() {
        return measurmentService.findAll().stream().map(this::convertToMeasurmentsDTO)
                .collect(Collectors.toList()); // Jackson конвертирует эти объекты в JSON
    }

    @GetMapping("/{id}")
    public MeasurmentDTO getMeasurment(@PathVariable("id") int id) {
        return convertToMeasurmentsDTO(measurmentService.findOne(id));
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurmentDTO measurmentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new MeasurmentNotCreatedExeption(errorMsg.toString());
        }
        measurmentService.save(convertToMeasurment(measurmentDTO));
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
