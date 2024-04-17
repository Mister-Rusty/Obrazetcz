package ru.russkikh.springcourse.FirstRestApp.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

public class SensorDTO {

    @NotEmpty(message = "Название сенсора не должно быть пустым")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
