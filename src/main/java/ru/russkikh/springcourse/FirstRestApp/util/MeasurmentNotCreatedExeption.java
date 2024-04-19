package ru.russkikh.springcourse.FirstRestApp.util;

public class MeasurmentNotCreatedExeption extends RuntimeException{
    public MeasurmentNotCreatedExeption(String msg) {
        super(msg);
    }
}