<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>    
    
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>
        <link rel="stylesheet" href="bootstrap.min.css">
        <link rel="stylesheet" href="stylesheet.css">
        <script src="bootstrap.min.js" defer></script>

    </head>
    <body class="login-body">
        <header>
            <div class="navbar navbar-expand-lg navbar-dark bg-primary">
                <div class="container-fluid">
                    <img class="navbar-brand" src="logof.png" style="width:200px;height:73px;" alt="logo"/>
                </div>
            </div>
        </header>
        
        <main>
            <br>
            <% String flash = (String) request.getAttribute("flash"); %>
			<% if (flash != null) { %>
			<div class="alert alert-dismissible alert-danger">
  				<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
  				<strong><%= flash %></strong>
			</div>
			
			<% } %>
            <div class="card text-white bg-info mb-3" style="padding: 15px; max-width: 20rem; margin-left: 37%; margin-top: 8%;">
                <div class="card-header">Iniciar Sesión</div>
                <div class="card-body">

                    <form action="logintry" method="post">   
                        <div class="form-group">
                            <input type="text" class="form-control" name = "username" placeholder="Ingrese su nombre" required maxlength="20">
                            <input type="password" class="form-control" name = "password" placeholder="Ingrese su contraseña" required maxlength="20">
                        </div>
                        <br>
                        <div class="input-group mb-3">
                            <button type="submit" class="btn btn-primary" style="margin-left: 33%">Ingresar</button>
                        </div>           
                    </form>
                    
                </div>
            </div>
        </main>

        <footer>
            <b>Secretaría de Turismo de la Tierra Media © 2021</b>
        </footer>
    </body>
</html>