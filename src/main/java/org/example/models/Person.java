package org.example.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class Person {

    private int id;

    @NotEmpty(message = "Name should not be empty")
    private String fio;

    @NotEmpty(message = "year born should not be empty")
    @Min(value = 1900)
    private int yearBorn;

    public Person() {}

    public Person(int id, String fio, int yearBorn) {
        this.id = id;
        this.fio = fio;
        this.yearBorn = yearBorn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getYearBorn() {
        return yearBorn;
    }

    public void setYearBorn(int yearBorn) {
        this.yearBorn = yearBorn;
    }
}
