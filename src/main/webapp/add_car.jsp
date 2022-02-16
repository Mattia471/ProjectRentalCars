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
                        <c:url var="userHome" value="ParkServlet"> <!--UTILIZZATO JSTL per il collegamento alla pagina e il richiamo della servlet-->
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
        <h2 class="text-center">Nuova Auto</h2>
        <form action="ParkServlet" method="POST">
            <div class="row" style="padding-top: 20px">
                <div class="col">
                    <b>Targa</b>
                    <input type="text"  name="licensePlate" class="form-control" required>
                </div>
                <div class="col">
                    <b>Marchio</b>
                    <input type="text" name="manufacturer" class="form-control" required>
                </div>
            </div>

            <div class="row">
                <div class="col">

                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col">
                    <b>Modello</b>
                    <input type="text"  name="model" class="form-control" required>
                </div>
                <div class="col">
                    <b>Tipo</b>
                    <input type="text" name="type" class="form-control" required>
                </div>
                <div class="col">
                    <b>Anno</b>
                    <input type="text" name="year" class="form-control" required>
                </div>
            </div><br>
            <div class="row">
                <div class="col">
                    <input type="submit" class="form-control btn btn-success" value="Inserisci nuova auto">
                </div>
            </div>
            <input type="text" name="azione" value="addCar" hidden>
        </form>
    </div>
</body>
</html>
