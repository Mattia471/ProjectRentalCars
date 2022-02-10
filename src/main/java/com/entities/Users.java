package com.entities;

import javax.persistence.*;
import java.util.Collection;
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

    @Column
    private String role;

    @Temporal(TemporalType.DATE)
    @Column
    private Date birthdate;

    //JOIN TRA TABELLA USERS E RESERVATIONS
    @OneToMany(mappedBy = "User")
    private List<Reservations> reservations;

    //constructor
    public Users(int id, String name, String surname, String email, String password, String role, Date birthdate, List<Reservations> reservations) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.birthdate = birthdate;
        this.reservations = reservations;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public List<Reservations> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservations> reservations) {
        this.reservations = reservations;
    }
}
