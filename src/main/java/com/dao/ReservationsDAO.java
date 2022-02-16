package com.dao;

import com.entities.Cars;
import com.entities.Reservations;
import com.entities.Users;
import com.hibernate.Config;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

public class ReservationsDAO {

    public static void insertReservation(Reservations reservations){

        Transaction transaction = null;
        try (Session session = Config.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();


            session.persist(reservations);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static Reservations getIDReservation(String reservationId){


        int ReservationID = Integer.parseInt(reservationId);
        Session session = Config.getSessionFactory().openSession();
        return session.get(Reservations.class, ReservationID);

    }


    public static void updateReservation(Reservations reservations, Date startDate, Date endDate){

        Transaction transaction = null;
        try (Session session = Config.getSessionFactory().openSession()) {

            // start a transaction
            transaction = session.beginTransaction();

            // Modifica le informazioni tramite i setter
            reservations.setStart_date(startDate);
            reservations.setEnd_date(endDate);

            session.merge(reservations);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    public static void deleteReservation(String reservationID){

        Transaction transaction = null;
        try (Session session = Config.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            Reservations reservations = getIDReservation(reservationID);

            session.delete(reservations);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void confirmedReservation(Reservations confirmed,String conferma){

        Transaction transaction = null;
        try (Session session = Config.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            // Modifica le informazioni tramite i setter
            confirmed.setStatus(conferma);


            session.merge(confirmed);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static List<Reservations> getReservationsUser(int idUser) {
        try (Session session = Config.getSessionFactory().openSession()) {
            return session.createQuery("FROM Reservations as U where userId ="+idUser, Reservations.class).list();

        }
    }

    //recupera lista degli utenti
    public static List<Reservations> getReservations(int idUser) {
        try (Session session = Config.getSessionFactory().openSession()) {
            return session.createQuery("FROM Reservations as U where userId ="+idUser, Reservations.class).list();

        }
    }


}
