package ru.russkikh.springcourse.FirstRestApp.dto;

import org.hibernate.validator.constraints.NotEmpty;
import ru.russkikh.springcourse.FirstRestApp.models.Sensor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class MeasurementDTO {

    @NotNull
    @Min(-100)
    @Max(100)
    private Double value;

    @NotNull
    private Boolean raining;

    @NotNull
    private Sensor sensor;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
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
