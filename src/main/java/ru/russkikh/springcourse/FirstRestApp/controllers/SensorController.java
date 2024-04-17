package ru.russkikh.springcourse.FirstRestApp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.russkikh.springcourse.FirstRestApp.dto.SensorDTO;
import ru.russkikh.springcourse.FirstRestApp.models.Sensor;
import ru.russkikh.springcourse.FirstRestApp.services.SensorService;
import ru.russkikh.springcourse.FirstRestApp.util.SensorErrorResponse;
import ru.russkikh.springcourse.FirstRestApp.util.SensorNotCreatedExeption;
import ru.russkikh.springcourse.FirstRestApp.util.SensorNotFoundExeption;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController // @Controller + @ResponseBody над каждым методом
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;

    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper) {
       this.sensorService = sensorService;
       this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<SensorDTO> getSensors() {
        return sensorService.findAll().stream().map(this::convertToSensorDTO)
                .collect(Collectors.toList()); // Jackson конвертирует эти объекты в JSON
    }

    @GetMapping("/{id}")
    public SensorDTO getPerson(@PathVariable("id") int id) {
        return convertToSensorDTO(sensorService.findOne(id)); // Jackson конвертирует в JSON
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
    StringBuilder errorMsg = new StringBuilder();
    List<FieldError> errors = bindingResult.getFieldErrors();
    for (FieldError error : errors) {
        errorMsg.append(error.getField())
                .append(" - ").append(error.getDefaultMessage())
                .append(";");
    }
    throw new SensorNotCreatedExeption(errorMsg.toString());
    }
    sensorService.save(convertToSensor(sensorDTO));
    return ResponseEntity.ok(HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleExeption(SensorNotFoundExeption e) {
        SensorErrorResponse response = new SensorErrorResponse("Сенсор не найден в БД", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleExeption(SensorNotCreatedExeption e) {
        SensorErrorResponse response = new SensorErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO,Sensor.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor,SensorDTO.class);
    }
}
