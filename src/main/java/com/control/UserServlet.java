package com.control;


import com.dao.UsersDAO;

import com.entities.Users;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "UserServlet", value = "/UserServlet")
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try { //viene eseguito il codice all'interno se c'è un errore si interrompe e passa al catch

            String azione = request.getParameter("azione"); //definisco la variabile passata da jsp per definire l'azione da fare

            if(azione == null){ //condizione quando utente logga in automatico visualizza la lista delle prenotazioni
                azione="list";
            }
            switch (azione) { //verifico il contenuto del parametro azione e a seconda del suo contenuto eseguo un azione
                case "login":
                    login(request, response);
                    break;

                case "list":
                    listUsers(request, response);
                    break;

                case "profile":
                    viewProfile(request, response);
                    break;

                case "logout":
                    logout(request, response);
                    break;
            }

        } catch (Exception e) { //se il codice da errori entra in questo blocco di codici e mostra l'errore
            throw new ServletException(e);
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsersDAO userDao = new UsersDAO();
        List<Users> customers = UsersDAO.getUsers();
        request.setAttribute("listUsers", customers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin_home.jsp");
        dispatcher.forward(request, response);

    }

    private void viewProfile(HttpServletRequest request, HttpServletResponse response) throws Exception{

        HttpSession session = request.getSession();
        Users users = (Users) session.getAttribute("user");
        request.setAttribute("user", users);
        RequestDispatcher dispatcher = request.getRequestDispatcher("users_profile.jsp");
        dispatcher.forward(request, response);

    }

    protected void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsersDAO usersDao = new UsersDAO(); //creo un istanza users

        //dichiarazioen variabili accesso
        String email = request.getParameter("email");
        String password = request.getParameter("password");


        try{
            Users userLog = usersDao.validateLogin(email, password); //utilizzo il metodo validatelogin in UsersDAO per validare email e password, assegna il volore di output a userLog
            HttpSession session = request.getSession();

            if(userLog!=null) { //se l'utente combacia con le credeziali esegui i codici di assegnazione sessione e reindirizzamento
                session.setAttribute("user", userLog); //assegna alla sessione "user" l'email
                session.setMaxInactiveInterval(5 * 60); //invalidala dopo 5 minuti

            if(userLog.isAdmin()) { //omesso ==true
                response.sendRedirect("UserServlet"); //accesso eseguito vai alla pagina
            }else{
                response.sendRedirect("ParkServlet"); //accesso eseguito vai alla pagina
            }

            }else{
                response.sendRedirect("login.jsp"); //accesso eseguito vai alla pagina

            }
        } catch (Exception e) { //se il codice da errori entra in questo blocco e mostra l'errore
            throw new ServletException(e);
        }

    }


    private void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();

        if (session != null){
            session.invalidate();
        }

        response.sendRedirect("login.jsp");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try { //viene eseguito il codice all'interno se c'è un errore si interrompe e passa al catch

            String azione = request.getParameter("azione"); //definisco la variabile passata da jsp per definire l'azione da fare

            if(azione == null){ //condizione quando utente logga in automatico visualizza la lista delle prenotazioni
                azione="listR";
            }

            switch (azione) { //verifico il contenuto del parametro azione e a seconda del suo contenuto eseguo un azione

                case "addUser":
                    addUser(request, response);
                    break;

                case "updateUser":
                    updateUser(request, response);
                    break;

                case "deleteUser":
                    deleteUser(request, response);
                    break;

            }

        } catch (Exception e) { //se il codice da errori entra in questo blocco di codici e mostra l'errore
            throw new ServletException(e);
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //lettura dati inviati da form
        String name= request.getParameter("name");
        String surname= request.getParameter("surname");
        String birth= request.getParameter("birthdate");
        String email= request.getParameter("email");
        String password= request.getParameter("password");

        // converte data
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date birthdate = format.parse(birth);


        Users users = new Users(name,surname,email,password,birthdate);
        UsersDAO.insertUser(users);

        response.sendRedirect("UserServlet?azione=list");
        return;
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws Exception{

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Users updateUsers = UsersDAO.getUser(request.getParameter("userId"));

        UsersDAO.updateUser(updateUsers, name, surname, email, password);


        response.sendRedirect("UserServlet?azione=profile");

    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //lettura dati inviati da form
        String userID= request.getParameter("userID");

        UsersDAO.deleteUser(userID);

        response.sendRedirect("UserServlet?azione=list");
        return;
    }
}