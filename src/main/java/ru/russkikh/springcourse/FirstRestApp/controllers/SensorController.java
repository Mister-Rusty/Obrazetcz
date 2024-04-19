package ru.russkikh.springcourse.FirstRestApp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.russkikh.springcourse.FirstRestApp.models.Sensor;
import ru.russkikh.springcourse.FirstRestApp.services.SensorService;


import javax.validation.Valid;

//@RestController // @Controller + @ResponseBody над каждым методом
@Controller
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;

    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper) {
       this.sensorService = sensorService;
       this.modelMapper = modelMapper;
    }



    @GetMapping("")
    public String index(Model model)  {
        model.addAttribute("sensor",sensorService.findAll());
        return "views/sensors/all_sensors";
    }

    @GetMapping("/{id}")
    public String getOneSensor(@PathVariable("id") int id, Model model) {
        model.addAttribute("sensor", sensorService.findOne(id));
        return "views/sensors/one_sensor";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("sensor") Sensor sensor) {
        return "views/sensors/new_sensor";
    }

    @PostMapping("/new")
    public String createNewSensor(@ModelAttribute("sensor") @Valid Sensor sensor,
                                BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "views/sensors/new_sensor";
        }
        sensorService.save(sensor);
        return "redirect:/sensors";
    }

    @GetMapping("/{id}/edit")
    public String editSensor(Model model, @PathVariable("id") int id) {
        model.addAttribute("sensor",sensorService.findOne(id));
        return "views/sensors/edit_sensor";
    }

    @PatchMapping("/{id}")
    public String updateSensor (@ModelAttribute("sensor") @Valid Sensor sensor, BindingResult bindingResult,
                              @PathVariable("id") int id) {
        if (bindingResult.hasErrors()){
            return "views/sensors/edit_sensor";
        }
        sensorService.update(id,sensor);
        return "redirect:/sensors";
    }

    @DeleteMapping("/{id}")
    public String deleteSensor(@PathVariable("id") int id) {
        sensorService.delete(id);
        return "redirect:/sensors";
    }

    /*
    @GetMapping("/{id}")
    public SensorDTO getSensor(@PathVariable("id") int id) {
        return convertToSensorDTO(sensorService.findOne(id));
    }
    @GetMapping()
    public List<SensorDTO> getSensors() {
        return sensorService.findAll().stream().map(this::convertToSensorDTO)
                .collect(Collectors.toList()); // Jackson конвертирует эти объекты в JSON
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
    }*/
}
