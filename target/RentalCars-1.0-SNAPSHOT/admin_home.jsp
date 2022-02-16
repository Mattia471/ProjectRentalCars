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
    <title>Home</title>
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



<div class="container-fluid">
    <div class="row" style="padding-top: 10px">
        <div class="col-8">
            <b>Benvenuto ${user.name} ${user.surname}</b>
        </div>
        <div class="col">
            <a href="add_user.jsp" class="btn btn-dark" style="position: absolute;right: 10px">Nuovo utente</a>
        </div>
    </div>
<hr>
    <table class="table" >
        <thead class="thead-dark table-striped">
        <tr>
            <th scope="col">Nome</th>
            <th scope="col">Cognome</th>
            <th scope="col">Email</th>
            <th scope="col">#</th>
            <th scope="col">#</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="tempUser" items="${listUsers}"> <!--UTILIZZARE JSTL SU JSP-->
            <tr>
                <td>${tempUser.name}</td>
                <td>${tempUser.surname}</td>
                <td>${tempUser.email}</td>
                <td><form action="UserServlet" method="GET">
                    <input type="text" name="azione" value="loadUser" hidden>
                    <input type="text" name="userID" value="${tempUser.id}" hidden>
                    <input type="submit" class="btn btn-success" value="Modifica">
                </form> </td>
                <td><form action="UserServlet" method="POST">
                    <input type="text" name="azione" value="deleteUser" hidden>
                    <input type="text" name="userID" value="${tempUser.id}" hidden>
                    <input type="submit" class="btn btn-danger" value="Elimina">
                </form> </td>
                <td><form action="ParkServlet" method="GET">
                    <input type="text" name="userID" value="${tempUser.id}" hidden>
                    <input type="text" name="azione" value="listR" hidden>
                    <input type="submit" class="btn btn-info text-white" value="Visualizza prenotazioni">
                </form> </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
