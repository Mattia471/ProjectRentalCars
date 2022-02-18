package com.control;

import com.dao.CarsDAO;
import com.dao.ReservationsDAO;
import com.dao.UsersDAO;
import com.entities.Cars;
import com.entities.Reservations;
import com.entities.Users;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ParkServlet", value = "/ParkServlet")
public class ParkServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try { //viene eseguito il codice all'interno se c'è un errore si interrompe e passa al catch

            String azione = request.getParameter("azione"); //definisco la variabile passata da jsp per definire l'azione da fare

            if(azione == null){ //condizione quando utente logga in automatico visualizza la lista delle prenotazioni
                azione="listR";
            }

            switch (azione) { //verifico il contenuto del parametro azione e a seconda del suo contenuto eseguo un azione

                case "listC":
                    listCar(request, response);
                    break;

                case "loadCar":
                    loadCar(request, response);
                    break;

                case "loadInfoCar":
                    loadInfoCar(request, response);
                    break;

                case "requestSearchCars":
                    requestSearchCars(request, response);
                    break;


            }

        } catch (Exception e) { //se il codice da errori entra in questo blocco di codici e mostra l'errore
            throw new ServletException(e);
        }
    }





    private void loadInfoCar(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String carID = request.getParameter("carID");

        Cars cars = CarsDAO.getCarId(carID);

        String comando= "edit";
        request.setAttribute("infoCar", cars);
        request.setAttribute("comando", comando);
        RequestDispatcher dispatcher = request.getRequestDispatcher("manageCar.jsp");
        dispatcher.forward(request, response);

    }

    private void listCar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Cars> cars = CarsDAO.getCars();
        request.setAttribute("listCar", cars);
        RequestDispatcher dispatcher = request.getRequestDispatcher("carPark.jsp"); //a quale file inviare i dati
        dispatcher.forward(request, response);

    }

    private void loadCar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Cars> cars = CarsDAO.getCars();
        request.setAttribute("listCar", cars);
        RequestDispatcher dispatcher = request.getRequestDispatcher("add_reservation.jsp"); //a quale file inviare i dati
        dispatcher.forward(request, response);
    }


    private void requestSearchCars(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String comando= request.getParameter("comando");

        //lettura dati inviati da form
        String startDate= request.getParameter("startDate");
        String endDate= request.getParameter("endDate");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date endD = format.parse(endDate);
        Date startD = format.parse(startDate);

        if(comando=="add") {


            if (startD.before(endD) && endD.after(startD)) { //se la data di inizio e prima della data di fine e viceversa

                List<Cars> cars = CarsDAO.getCarsAvailable(endD, startD);
                request.setAttribute("listCar", cars);
                request.setAttribute("startDate", startDate);
                request.setAttribute("endDate", endDate);
                // Send to jsp page
                RequestDispatcher dispatcher = request.getRequestDispatcher("add_reservation.jsp");
                dispatcher.forward(request, response);
            } else { //altrimenti
                String errorLog = "Date inserite non valide, per favore reinseriscile correttamente!";
                request.setAttribute("errorLog", errorLog);

                RequestDispatcher dispatcher = request.getRequestDispatcher("page_error.jsp");
                dispatcher.forward(request, response);
            }


        }else{
            if (startD.before(endD) && endD.after(startD)) { //se la data di inizio e prima della data di fine e viceversa

                String reservationID= request.getParameter("reservationID");

                List<Cars> cars = CarsDAO.getCarsAvailable(endD, startD);
                request.setAttribute("listCar", cars);
                request.setAttribute("startDate", startDate);
                request.setAttribute("endDate", endDate);
                request.setAttribute("reservationID", reservationID);
                // Send to jsp page
                RequestDispatcher dispatcher = request.getRequestDispatcher("add_reservation.jsp");
                dispatcher.forward(request, response);
            } else { //altrimenti
                String errorLog = "Date inserite non valide, per favore reinseriscile correttamente!";
                request.setAttribute("errorLog", errorLog);

                RequestDispatcher dispatcher = request.getRequestDispatcher("page_error.jsp");
                dispatcher.forward(request, response);
            }
        }


    }

































    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try { //viene eseguito il codice all'interno se c'è un errore si interrompe e passa al catch

            String azione = request.getParameter("azione"); //definisco la variabile passata da jsp per definire l'azione da fare


            switch (azione) { //verifico il contenuto del parametro azione e a seconda del suo contenuto eseguo un azione

                case "manageCar":
                    manageCar(request, response);
                    break;

                case "deleteCar":
                    deleteCar(request, response);
                    break;
            }

        } catch (Exception e) { //se il codice da errori entra in questo blocco di codici e mostra l'errore
            throw new ServletException(e);
        }
    }





    private void manageCar(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //lettura secondo comando
        String comando= request.getParameter("comando");

        //lettura dati inviati da form
        String licensePlate= request.getParameter("licensePlate");
        String manufacturer= request.getParameter("manufacturer");
        String model= request.getParameter("model");
        String type= request.getParameter("type");
        String year= request.getParameter("year");

        switch (comando) { //verifico il contenuto del parametro azione e a seconda del suo contenuto eseguo un azione

            case "add":

                Cars cars = new Cars(type,manufacturer,model,year,licensePlate);
                CarsDAO.insertCar(cars);

                break;

            case "edit":
                // recupera l'id della prenotazione
                Cars updateCar = CarsDAO.getCarId(request.getParameter("carID"));

                CarsDAO.updateCar(updateCar,licensePlate,manufacturer,model,type,year);
                break;
        }




        response.sendRedirect("ParkServlet?azione=listC");
    }


    private void deleteCar(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //lettura dati inviati da form
        String carID= request.getParameter("carID");

        CarsDAO.deleteCar(carID);

        response.sendRedirect("ParkServlet?azione=listC");
    }
}
