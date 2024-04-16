package ru.russkikh.springcourse.FirstRestApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.russkikh.springcourse.FirstRestApp.models.Sensor;
import ru.russkikh.springcourse.FirstRestApp.services.SensorService;
import ru.russkikh.springcourse.FirstRestApp.util.SensorErrorResponse;
import ru.russkikh.springcourse.FirstRestApp.util.SensorNotFoundExeption;

import java.util.List;


@RestController // @Controller + @ResponseBody над каждым методом
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
       this.sensorService = sensorService;
    }

    @GetMapping()
    public List<Sensor> getSensors() {
        return sensorService.findAll(); // Jackson конвертирует эти объекты в JSON
    }

    @GetMapping("/{id}")
    public Sensor getPerson(@PathVariable("id") int id) {
        return sensorService.findOne(id); // Jackson конвертирует в JSON
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleExeption(SensorNotFoundExeption e) {
        SensorErrorResponse response = new SensorErrorResponse("Сенсор не найден в БД", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
