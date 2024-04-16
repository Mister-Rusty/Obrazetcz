package ru.russkikh.springcourse.FirstRestApp.util;

public class SensorNotCreatedExeption extends RuntimeException{
    public SensorNotCreatedExeption(String msg) {
        super(msg);
    }
}
