<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>

    <title>Login Rental Cars</title>
    <!-- CSS only -->
    <link rel='stylesheet' href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>
</head>
<body>
<div class="container">
    <div class="row">
        <center>
            <div class="col-6">
                <h1>Login Rental Cars</h1>
                <form action="UserServlet" method="GET" style="padding: 20px;">
                    <b class="text-uppercase">Email:</b>
                    <input type="email" class="form-control" name="email"><br>
                    <b class="text-uppercase">Password:</b><br>
                    <input type="password"  class="form-control" name="password"><br>
                    <input type="submit" class="btn btn-success" name="send" value="Accedi">
                    <input type="text" name="azione" value="login" hidden>
                </form>
            </div>
        </center>
    </div>
</div>

</body>
</html>
