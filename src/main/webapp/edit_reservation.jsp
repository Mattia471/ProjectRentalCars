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
    <title>Modifca Prenotazione</title>
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



    <div class="container">
        <h2 class="text-center">Modifica Prenotazione</h2>
        <form action="ParkServlet" method="POST">
            <div class="row" style="padding-top: 20px">
                <div class="col">
                    <b>Data di inizio noleggio</b>
                    <input type="date"  name="newStartDate" class="form-control" required>
                </div>
                <div class="col">
                    <b>Data di consegna</b>
                    <input type="date" name="newEndDate" class="form-control" required>
                </div>
            </div>

            <div class="row">
                <div class="col">
                    <table class="table" >
                        <thead class="thead-dark table-striped">
                        <tr>
                            <th scope="col">Targa</th>
                            <th scope="col">Marchio</th>
                            <th scope="col">Modello</th>
                            <th scope="col">Tipo</th>
                            <th scope="col">Anno</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="tempCar" items="${listCar}"> <!--UTILIZZARE JSTL SU JSP-->
                            <tr>
                                <th scope="row"><input type="radio" name="car" value="${tempCar.id}"></th>
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
            <br>
            <div class="row">
                <div class="col">
                    <input type="submit" class="form-control btn btn-success" value="Conferma Prenotazione">
                </div>
            </div>
            <input type="text" name="azione" value="editReservation" hidden>
        </form>
    </div>
</body>
</html>
