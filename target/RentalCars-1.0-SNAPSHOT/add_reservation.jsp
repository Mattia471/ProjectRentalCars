<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!--viene settata la lingua locale -->
<c:set var="theLocale"
       value="${not empty param.theLocale ? param.theLocale : pageContext.request.locale}"
       scope="session" />

<!--inclusiome file .properties per il linguaggio dei label-->
<fmt:setLocale value="${theLocale}" />
<fmt:setBundle basename="labels" />
<html>
<head>
    <title>Nuova Prenotazione</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

</head>
<body class="bg-light">



<nav class="navbar navbar-expand-lg navbar-light bg-dark" style="padding: 20px">
    <a class="navbar-brand text-white"><fmt:message key="header.button1" /></a>

    <div class="collapse navbar-collapse " id="navbarSupportedContent" style="padding-left: 35%">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <c:choose>
                    <c:when test="${user.isAdmin}">
                        <c:url var="userHome" value="UserServlet"> <!--UTILIZZATO JSTL per il collegamento alla pagina e il richiamo della servlet-->
                            <c:param name="azione" value="list"/>
                        </c:url>
                    </c:when>
                    <c:otherwise>
                        <c:url var="userHome" value="ReservationServlet"> <!--UTILIZZATO JSTL per il collegamento alla pagina e il richiamo della servlet-->
                            <c:param name="azione" value="listR"/>
                        </c:url>
                    </c:otherwise>
                </c:choose>
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




    <div class="container">
        <h2 class="text-center">Scegli l'auto da noleggiare</h2>
        <c:choose>
            <c:when test="${empty(reservationID)}">
            </c:when>
            <c:otherwise>
                <center><b>Se l'auto che avevi gia scelto non risulta nell'elenco è gia prenotata, puoi comunque sceglierne un'altra</b></center>
            </c:otherwise>
        </c:choose>
        <form action="ReservationServlet" method="POST">

            <div class="row">
                <div class="col">
                    <table class="table" >
                        <thead class="thead-dark table-striped">
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Targa</th>
                            <th scope="col">Marchio</th>
                            <th scope="col">Modello</th>
                            <th scope="col">Tipo</th>
                            <th scope="col">Anno</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="tempCar" items="${listCar}"> <!--UTILIZZARE JSTL SU JSP-->
                            <tr class="bg-success text-white">
                                <th scope="row"><input type="radio" name="car" value="${tempCar.id}" required></th>
                                <td>${tempCar.licensePlate}</td>
                                <td>${tempCar.manufacturer}</td>
                                <td>${tempCar.model} </td>
                                <td>${tempCar.type} </td>
                                <td>${tempCar.year} </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <br><hr>
            <div class="row">
                <b>Date scelte per il noleggio</b>
                <div class="col">
                    <input type="date" name="startDate" class="form-control border-success" value="${startDate}" >
                </div>
                <div class="col">
                    <input type="date" name="endDate" class="form-control border-success" value="${endDate}" >
                </div>
            </div><br><hr>
            <div class="row">
                <div class="col">
                    <input type="submit" class="form-control btn btn-success" value="Conferma Prenotazione">
                </div>
            </div>
            <c:choose>
                <c:when test="${empty(reservationID)}">
                    <input type="text" name="comando" value="add" hidden>
                </c:when>
                <c:otherwise>
                    <input type="text" name="comando" value="edit" hidden>
                    <input type="text" name="reservationID" value="${reservationID}" hidden>
                </c:otherwise>
            </c:choose>
            <input type="text" name="azione" value="manageReservation" hidden>
        </form>
    </div>
</body>
</html>
