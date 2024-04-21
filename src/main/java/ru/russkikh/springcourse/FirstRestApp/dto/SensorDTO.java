package ru.russkikh.springcourse.FirstRestApp.dto;

import javax.persistence.Column;
import javax.validation.constraints.*;

public class SensorDTO {

    @NotNull
    @Min(-100)
    @Max(100)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
