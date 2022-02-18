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
    <title>Parco Auto</title>
    <link rel='stylesheet' href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>

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
            <<li class="nav-item">
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
            <b style="font-size: 25px"><fmt:message key="label.carPark1" /> </b>
        </div>
        <div class="col">
            <c:choose>
                <c:when test="${user.isAdmin}">
                    <a href="manageCar.jsp" class="btn btn-dark" style="position: absolute;right: 10px"><fmt:message key="label.carPark" /></a>
                </c:when>
            </c:choose>
        </div>
    </div>

    <table class="table" >
        <thead class="thead-dark table-striped">
        <tr>
            <th scope="col">Targa</th>
            <th scope="col">Modello</th>
            <th scope="col">Tipo</th>
            <th scope="col">Anno</th>
            <th scope="col"></th>
            <th scope="col"></th>

        </tr>
        </thead>
        <tbody>
        <c:forEach var="tempCar" items="${listCar}"> <!--UTILIZZARE JSTL SU JSP-->
            <tr>
                <td>${tempCar.licensePlate}</td>
                <td>${tempCar.model}</td>
                <td>${tempCar.type}</td>
                <td>${tempCar.year} </td>
                <c:choose>
                    <c:when test="${user.isAdmin}">
                        <td><form action="ParkServlet" method="GET">
                            <input type="text" name="azione" value="loadInfoCar" hidden>
                            <input type="text" name="carID" value="${tempCar.id}" hidden>
                            <input type="submit" class="btn btn-success" value="Modifica">
                        </form> </td>
                        <td><form action="ParkServlet" method="POST">
                            <input type="text" name="azione" value="deleteCar" hidden>
                            <input type="text" name="carID" value="${tempCar.id}" hidden>
                            <input type="submit" class="btn btn-danger" value="Elimina">
                        </form> </td>
                    </c:when>
                </c:choose>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
