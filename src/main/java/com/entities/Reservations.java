package com.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
//@Table(name = "users") usata solo se il nome della classe è diverso dal nome dalla tabella di creare
public class Reservations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Temporal(TemporalType.DATE)
    @Column
    private Date start_date;

    @Temporal(TemporalType.DATE)
    @Column
    private Date end_date;


    //join con tabella USERS
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private Users User;

    //join con tabella CARS
    @OneToOne
    @JoinColumn(name="car_id", referencedColumnName = "id")
    private Cars Car;

    @Column
    private String status;

    //constructor
    public Reservations(int id, Date start_date, Date end_date, Users user, Cars car, String status) {
        this.id = id;
        this.start_date = start_date;
        this.end_date = end_date;
        User = user;
        Car = car;
        this.status = status;
    }

    //get and set
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Users getUser() {
        return User;
    }

    public void setUser(Users user) {
        User = user;
    }

    public Cars getCar() {
        return Car;
    }

    public void setCar(Cars car) {
        Car = car;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
