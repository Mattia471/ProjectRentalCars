package com.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cars") //usata solo se il nome della classe è diverso dal nome dalla tabella di creare
public class Cars  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String type;

    @Column
    private String manufacturer;

    @Column
    private String model;

    @Column
    private String year;

    @Column
    private String licensePlate;

    //JOIN TRA TABELLA CARS E RESERVATIONS
    @OneToMany(mappedBy = "Car", cascade = CascadeType.ALL)
    private List<Reservations> reservations;


    //constructor
    public Cars(String type, String manufacturer, String model, String year, String license_plate) {
        this.type = type;
        this.manufacturer = manufacturer;
        this.model = model;
        this.year = year;
        this.licensePlate = license_plate;
    }

    public Cars() {

    }

    //get and set
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public List<Reservations> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservations> reservations) {
        this.reservations = reservations;
    }
}
