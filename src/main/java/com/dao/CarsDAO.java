package com.dao;

import com.entities.Cars;
import com.hibernate.Config;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
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


    public static Cars getCarId(String carId){
        int idCar = Integer.parseInt(carId);
        try (Session session = Config.getSessionFactory().openSession()) {
            return session.get(Cars.class, idCar);
        }

    }

    public static void updateCar(Cars cars, String licensePlate, String manufacturer,String model,String type,String year){

        Transaction transaction = null;
        try (Session session = Config.getSessionFactory().openSession()) {

            // start a transaction
            transaction = session.beginTransaction();

            // Modifica le informazioni tramite i setter
            cars.setLicensePlate(licensePlate);
            cars.setManufacturer(manufacturer);
            cars.setModel(model);
            cars.setType(type);
            cars.setYear(year);

            session.merge(cars);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void deleteCar(String carID){

        Transaction transaction = null;
        try (Session session = Config.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            Cars cars = getCarId(carID);

            session.delete(cars);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    //recupera lista delle auto
    public static List<Cars> getCars() {
        try (Session session = Config.getSessionFactory().openSession()) {
            return session.createQuery("FROM Cars ", Cars.class).list();

        }
    }


    //recupera lista delle auto disponibili
    public static List<Cars> getCarsAvailable(Date endD, Date startD) {
        try (Session session = Config.getSessionFactory().openSession()) {
            return session.createQuery("FROM Cars as C where not exists (from Reservations as R where R.car = C " +
                            "and R.startDate= :startD and R.endDate= :endD " +
                            "or R.startDate<= :startD and R.endDate>= :startD  " +
                            "or R.startDate>= :startD and R.startDate<= :endD and R.endDate>= :endD )", Cars.class)
                    .setParameter("startD", startD)
                    .setParameter("endD", endD)
                    .list();

        }
    }
}
