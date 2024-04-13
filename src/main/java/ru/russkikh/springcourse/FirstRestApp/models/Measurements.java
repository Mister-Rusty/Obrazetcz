package ru.russkikh.springcourse.FirstRestApp.models;


import javax.persistence.*;

@Entity
@Table(name = "Measurments")
public class Measurements {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "value")
    private int value;

    private boolean raining;
    
    private Sensor sensor;



}
