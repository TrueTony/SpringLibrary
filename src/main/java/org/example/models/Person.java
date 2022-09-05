package org.example.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fio")
    @NotEmpty(message = "Имя не должно быть пустое")
    private String fio;

    @Column(name = "yearborn")
    @Min(value = 1900, message = "Год рождения должен быть больше или равен 1900")
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
