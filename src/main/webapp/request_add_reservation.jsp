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
        <h2 class="text-center">Scegli le date in cui noleggiare un auto</h2>



        <form action="ParkServlet" method="GET">
            <div class="row" style="padding-top: 20px">
                <div class="col">
                    <b>Data di inizio noleggio</b>
                    <input type="date"  name="startDate" class="form-control" required>
                </div>
                <div class="col">
                    <b>Data di consegna</b>
                    <input type="date" name="endDate" class="form-control" required>
                </div>
            </div>

            <br>
            <div class="row">
                <div class="col">
                    <input type="submit" class="form-control btn btn-success" value="Scegli l'auto da noleggiare">
                </div>
            </div>
            <input type="text" name="comando" value="add" hidden>
            <input type="text" name="azione" value="requestSearchCars" hidden>
        </form>
    </div>
</body>
</html>
