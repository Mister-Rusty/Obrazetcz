package ru.russkikh.springcourse.FirstRestApp.models;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Measurements")
public class Measurements {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Integer;

    @Column(name = "value")
    @NotEmpty
    @Size(min = -100, max = 100, message = "Вне диапазона температур")
    private Integer value;

    @Column(name="raining")
    @NotEmpty
    private Boolean raining;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "sensor_name", referencedColumnName = "name")
    private Sensor sensor;


    public int getInteger() {
        return Integer;
    }

    public void setInteger(int integer) {
        Integer = integer;
    }

    public java.lang.Integer getValue() {
        return value;
    }

    public void setValue(java.lang.Integer value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
