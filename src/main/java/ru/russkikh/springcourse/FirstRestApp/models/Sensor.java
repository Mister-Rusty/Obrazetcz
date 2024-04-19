package ru.russkikh.springcourse.FirstRestApp.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Sensor")
public class Sensor implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Название сенсора не должно быть пустым")
    @Size(min = 3,max = 30,message = "Имя сенсора должно содержать от 3 до 30 символов")
    private String name;

    @OneToMany(mappedBy = "sensor")
    private List<Measurements> measurements;

    public Sensor() {
    }

    public Sensor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Measurements> getMeasurementsList() {
        return measurements;
    }

    public void setMeasurementsList(List<Measurements> measurements) {
        this.measurements = measurements;
    }
}
