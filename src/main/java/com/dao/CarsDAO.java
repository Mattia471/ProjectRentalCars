package com.dao;

import com.entities.Cars;
import com.hibernate.Config;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CarsDAO {

    public static void insertCar(Cars cars){

        Transaction transaction = null;
        try (Session session = Config.getSessionFactory().openSession()) { //viene richiesta la sessionFactory e aperta una nuova sessione

            // inizia la transazione
            transaction = session.beginTransaction();

            //inserisce i valori tramite query sql presavata nel metodo (persist)
            session.persist(cars);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static Cars getCarId(String carId) {
        if (carId != null) {

            //trasforma valore in tipo int tramite integer.
            int idCar = Integer.parseInt(carId);

            //recupera sessione
            Session session = Config.getSessionFactory().openSession();

            //invia la richiesta
            Cars cars =  (Cars) session.get( Cars.class, new Integer(carId) );
            return cars;

        }
        return null;
    }

    //recupera lista delle auto
    public static List<Cars> getCars() {
        try (Session session = Config.getSessionFactory().openSession()) {
            return session.createQuery("FROM Cars ", Cars.class).list();

        }
    }
}
