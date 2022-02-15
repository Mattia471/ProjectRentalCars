package com.dao;

import com.entities.Reservations;
import com.entities.Users;
import com.hibernate.Config;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UsersDAO {

    public static void insertUser(Users users){

        Transaction transaction = null;
        try (Session session = Config.getSessionFactory().openSession()) {

            // start a transaction
            transaction = session.beginTransaction();

            // save the student object
            session.persist(users);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void updateUser(Users user,String name,String surname,String email,String password){

        Transaction transaction = null;
        try (Session session = Config.getSessionFactory().openSession()) {

            // start a transaction
            transaction = session.beginTransaction();

            // Modifica le informazioni tramite i setter
            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setPassword(password);

            session.merge(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void deleteUser(String userID){

        Transaction transaction = null;
        try (Session session = Config.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            Users users = getUser(userID);

            session.delete(users);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    public static Users getUser(String userID) {
        int userId = Integer.parseInt(userID);
        Session session = Config.getSessionFactory().openSession();
        return session.get(Users.class, userId);

    }
    //recupera lista degli utenti
    public static List<Users> getUsers() {
        try (Session session = Config.getSessionFactory().openSession()) {
            return session.createQuery("FROM Users as U WHERE U.isAdmin = false ", Users.class).list();

        }
    }

    public Users validateLogin(String email, String password){

        Transaction transaction = null;
        Users users = null;
        Session session = Config.getSessionFactory().openSession();
        try{
            //inizio transazione tra dao e db
            transaction = session.beginTransaction();
            //ottieni valori utente
            users = (Users) session.createQuery("FROM Users U WHERE U.email= :email").setParameter("email", email).uniqueResult();

            if(users != null && users.getPassword().equals((password))){
                return users;
            }
            transaction.commit(); //fine di una transazione
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback(); // consente di cancellare tutte le modifiche dei dati eseguite dall'inizio della transazione
                e.printStackTrace();
            }
        }
        return null;
    }
    
}
