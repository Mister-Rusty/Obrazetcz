package ru.russkikh.springcourse.FirstRestApp.dto;

import ru.russkikh.springcourse.FirstRestApp.models.Sensor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class MeasurmentDTO {

    @NotEmpty
    @Size(min = -100, max = 100, message = "Вне диапазона температур")
    private int value;

    @NotEmpty
    private boolean raining;

    @NotEmpty
    private Sensor sensor;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
