package ru.russkikh.springcourse.FirstRestApp.dto;

import ru.russkikh.springcourse.FirstRestApp.models.Sensor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class MeasurmentDTO {

    @NotEmpty
    @Size(min = -100, max = 100, message = "Вне диапазона температур")
    private Integer value;

    @NotEmpty
    private Boolean raining;

    @NotEmpty
    private Sensor sensor;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
