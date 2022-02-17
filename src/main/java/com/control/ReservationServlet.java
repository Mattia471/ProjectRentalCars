package com.control;

import com.dao.CarsDAO;
import com.dao.ReservationsDAO;
import com.entities.Cars;
import com.entities.Reservations;
import com.entities.Users;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ReservationServlet", value = "/ReservationServlet")
public class ReservationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try { //viene eseguito il codice all'interno se c'è un errore si interrompe e passa al catch

            String azione = request.getParameter("azione"); //definisco la variabile passata da jsp per definire l'azione da fare

            if(azione == null){ //condizione quando utente logga in automatico visualizza la lista delle prenotazioni
                azione="listR";
            }

            switch (azione) { //verifico il contenuto del parametro azione e a seconda del suo contenuto eseguo un azione
                case "listR":
                    listReservations(request, response);
                    break;

                case "loadReservation":
                    loadReservation(request, response);
                    break;
            }

        } catch (Exception e) { //se il codice da errori entra in questo blocco di codici e mostra l'errore
            throw new ServletException(e);
        }
    }




    private void listReservations(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //Recuperi la sessione dell'utente
        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("user");



        //controllo se utente admin o customer tramite campo su db bool
        if(currentUser.isAdmin()){
            //converto in int
            int userID= Integer.parseInt(request.getParameter("userID"));
            //creo un oggetto lista e richiamo il metodo statico tramite la classe
            List<Reservations> reservations = ReservationsDAO.getReservationsUser(userID);
            request.setAttribute("listReservations", reservations);
            RequestDispatcher dispatcher = request.getRequestDispatcher("list_reservations.jsp");
            dispatcher.forward(request, response);
        }else {
            //assegno alla data la data di oggi per utilizzarlo in jstl nel calcolo della diferrenza di data
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date now = format.parse(format.format(new Date()));
            request.setAttribute("now", now);

            //creo un oggetto lista e richiamo il metodo statico tramite la classe
            List<Reservations> reservations = ReservationsDAO.getReservations(currentUser.getId());
            request.setAttribute("listReservations", reservations);
            RequestDispatcher dispatcher = request.getRequestDispatcher("user_home.jsp"); //a quale file inviare i dati
            dispatcher.forward(request, response);
        }


    }

    private void loadReservation(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String reservationID = request.getParameter("reservationID");

        Reservations reservations = ReservationsDAO.getIDReservation(reservationID);

        request.setAttribute("infoReservation", reservations);
        RequestDispatcher dispatcher = request.getRequestDispatcher("edit_reservation.jsp");
        dispatcher.forward(request, response);

    }























    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try { //viene eseguito il codice all'interno se c'è un errore si interrompe e passa al catch

            String azione = request.getParameter("azione"); //definisco la variabile passata da jsp per definire l'azione da fare


            switch (azione) { //verifico il contenuto del parametro azione e a seconda del suo contenuto eseguo un azione

                case "manageReservation":
                    manageReservation(request, response);
                    break;

                case "deleteReservation":
                    deleteReservation(request, response);
                    break;

                case "confirmedReservation":
                    confirmedReservation(request, response);
                    break;


            }

        } catch (Exception e) { //se il codice da errori entra in questo blocco di codici e mostra l'errore
            throw new ServletException(e);
        }
    }




    private void manageReservation(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //lettura secondo comando
        String comando= request.getParameter("comando");

        //Recuperi la sessione dell'utente
        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("user");

        //lettura dati inviati da form
        String startDate= request.getParameter("startDate");
        String endDate= request.getParameter("endDate");
        String car= request.getParameter("car");

        // converte le date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date endD = format.parse(endDate);
        Date startD = format.parse(startDate);

        switch (comando) { //verifico il contenuto del parametro azione e a seconda del suo contenuto eseguo un azione

            case "add":
                    // recupera l'id del veicolo
                    Cars cars = CarsDAO.getCarId(request.getParameter("car"));

                    //crea l'istanza per l'inserimento
                    Reservations reservations = new Reservations(startD,endD,currentUser,cars, "IN ATTESA");
                    ReservationsDAO.insertReservation(reservations);
                break;

            case "edit":
                // recupera l'id della prenotazione
                Reservations updateReservation = ReservationsDAO.getIDReservation(request.getParameter("reservationID"));
                ReservationsDAO.updateReservation(updateReservation, startD, endD);
                break;
        }
        response.sendRedirect("ReservationServlet?azione=listR");
        return;
    }





    private void deleteReservation(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //lettura dati inviati da form
        String reservationID= request.getParameter("reservationID");

        ReservationsDAO.deleteReservation(reservationID);

        response.sendRedirect("ReservationServlet?azione=listR");
        return;
    }

    private void confirmedReservation(HttpServletRequest request, HttpServletResponse response) throws Exception{

        String conferma = request.getParameter("conferma");

        Reservations confirmed = ReservationsDAO.getIDReservation(request.getParameter("reservationID"));

        ReservationsDAO.confirmedReservation(confirmed, conferma);


        response.sendRedirect("UserServlet?azione=list");

    }
}
