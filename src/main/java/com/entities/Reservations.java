package com.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservations") //usata solo se il nome della classe è diverso dal nome dalla tabella di creare
public class Reservations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Temporal(TemporalType.DATE)
    @Column
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column
    private Date endDate;


    //join con tabella USERS
    @ManyToOne
    @JoinColumn(name="userId", nullable = false)
    private Users user;

    //join con tabella CARS
    @ManyToOne
    @JoinColumn(name="carId", nullable = false)
    private Cars car;

    @Column
    private String status;

    //constructor
    public Reservations(Date startDate, Date endDate, Users user, Cars car, String status) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.car = car;
        this.status = status;
    }

    public Reservations() {

    }

    //get and set
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        user = user;
    }

    public Cars getCar() {
        return car;
    }

    public void setCar(Cars car) {
        car = car;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
