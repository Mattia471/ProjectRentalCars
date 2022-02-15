package com.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
//@Table(name = "users") usata solo se il nome della classe è diverso dal nome dalla tabella di creare
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String name;

    @Column//inserire solo (name= "") se il nome è diverso dalla variabile
    private String surname;

    @Column
    private String email;

    @Column
    private String password;


    @Temporal(TemporalType.DATE)
    @Column
    private Date birthdate;

    //JOIN TRA TABELLA USERS E RESERVATIONS
    @OneToMany(mappedBy = "User")
    private Reservations reservations;

    @Column
    private boolean isAdmin;

    //constructor
    public Users(String name, String surname, String email, String password, Date birthdate) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.isAdmin=false;
        this.birthdate = birthdate;
    }

    public Users() {

    }


    //get and set
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Reservations getReservations() {
        return reservations;
    }

    public void setReservations(Reservations reservations) {
        this.reservations = reservations;
    }

}
