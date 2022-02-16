<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!--viene recuperata la lingua locale -->
<c:set var="theLocale"
       value="${not empty param.theLocale ? param.theLocale : pageContext.request.locale}"
       scope="session" />

<!--inclusiome file .properties per il linguaggio dei label-->
<fmt:setLocale value="${theLocale}" />
<fmt:setBundle basename="labels" />
<html>
<head>
    <title>Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body class="bg-light">



<nav class="navbar navbar-expand-lg navbar-light bg-dark" style="padding: 20px">
    <a class="navbar-brand text-white"><fmt:message key="header.button1" /></a>

    <div class="collapse navbar-collapse " id="navbarSupportedContent" style="padding-left: 35%">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <c:url var="userHome" value="ParkServlet"> <!--UTILIZZATO JSTL per il collegamento alla pagina e il richiamo della servlet-->
                    <c:param name="azione" value="listR"/>
                </c:url>
                <a class="nav-link text-white" href="${userHome}"><fmt:message key="header.button2" /></a>
            </li>
            <li class="nav-item">
                <c:url var="carPark" value="ParkServlet"> <!--UTILIZZATO JSTL per il collegamento alla pagina e il richiamo della servlet-->
                    <c:param name="azione" value="listC"/>
                </c:url>
                <a class="nav-link text-white" href="${carPark}"><fmt:message key="header.button3" /></a>
            </li>
            <li class="nav-item">
                <c:url var="userProfile" value="UserServlet"> <!--UTILIZZATO JSTL per il collegamento alla pagina e il richiamo della servlet-->
                    <c:param name="azione" value="profile"/>
                </c:url>
                <a class="nav-link text-white" href="${userProfile}"><fmt:message key="header.button4" /></a>
            </li>
            <li class="nav-item" style="position: absolute;right:10px">
                <a class="btn btn-success my-2 my-sm-0 text-white" href="UserServlet?azione=logout">Logout</a>
            </li>
        </ul>
    </div>
</nav>



<div class="container-fluid">
    <div class="row" style="padding-top: 10px">
        <div class="col">
            <b style="font-size: 25px">Prenotazioni </b>
        </div>
        <div class="col">
            <c:url var="newR" value="ParkServlet"> <!--UTILIZZATO JSTL per il collegamento alla pagina e il richiamo della servlet-->
                <c:param name="azione" value="loadCar"/>
            </c:url>
            <a href="${newR}" class="btn btn-dark" style="position: absolute;right: 10px">Nuova Prenotazione</a>
        </div>
    </div>

    <table class="table" >
        <thead class="thead-dark table-striped">
            <tr>
                <th scope="col">#</th>
                <th scope="col">Durata</th>
                <th scope="col">Stato</th>
                <th scope="col">#</th>
                <th scope="col">#</th>
            </tr>
        </thead>
        <tbody>

        <c:forEach var="tempReservation" items="${listReservations}"> <!--UTILIZZARE JSTL SU JSP-->
            <tr>
                <!--tramite parseNumber assegno ad una variabile differenceDays la differenza tra la data di inizio noleggio e la data di oggi passata da servlet now diviso per (secondi*minuti*ore)/1000 ottenendo i millisecondi-->
                <fmt:parseNumber var="differenceDays" value="${((tempReservation.start_date.time - now.time) / (1000*60*60*24)) /10 }" />
                <th scope="row">1</th>
                <td>Dal ${tempReservation.start_date} al ${tempReservation.end_date} </td>
                <td>
                <c:choose>
                    <c:when test="${tempReservation.status != 'CONFERMATO'}">
                        <span class="badge badge-sm bg-danger">${tempReservation.status}</span>
                    </c:when>
                    <c:otherwise>
                        <span class="badge badge-sm bg-success">${tempReservation.status}</span>
                    </c:otherwise>
                </c:choose>
                </td>
                <c:choose>
                    <c:when test="${differenceDays >= 2}"> <!--CONTROLLO SE I GIORNI RIMANENTI SONO PIU' O MENO DI 2-->
                        <td><form action="ParkServlet" method="GET">
                            <input type="text" name="azione" value="loadReservation" hidden>
                            <input type="text" name="reservationID" value="${tempReservation.id}" hidden>
                            <input type="submit" class="btn btn-success" value="Modifica">
                        </form> </td>
                        <td><form action="ParkServlet" method="POST">
                            <input type="text" name="azione" value="deleteReservation" hidden>
                            <input type="text" name="reservationID" value="${tempReservation.id}" hidden>
                            <input type="submit" class="btn btn-danger" value="Elimina">
                        </form> </td>
                    </c:when>
                    <c:otherwise>
                        <td></td>
                    </c:otherwise>
                </c:choose>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
