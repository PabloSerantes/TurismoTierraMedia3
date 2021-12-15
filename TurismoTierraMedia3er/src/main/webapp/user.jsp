<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "controller.ListarPreferenciasOfertasServlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Turismo Tierra Media</title>
    <link rel="stylesheet" href="bootstrap.min.css">
    <link rel="stylesheet" href="stylesheet.css">
    <script src="bootstrap.min.js" defer></script>
</head>
<body class="user-body">
    <header>
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container-fluid">
                <a href="user.html"><img class="navbar-brand" src="logof.png" style="width:200px;height:73px;" alt="logo"/></a>
              <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
              </button>         
              <div class="collapse navbar-collapse" id="navbarColor01">
                <ul class="navbar-nav me-auto">
                  <li class="nav-item">
                    <a class="nav-link" href="user.html">Inicio</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" href="#">Ofertas</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" href="#">Mis Compras</a>
                  </li>
                </ul>
                <a href="logout" style="color: black;">Cerrar Sesion <img src="logout.png" style="width:40px;height:30px;" alt="Logout"></a>
              </div>
            </div>
          </nav>
    </header>
    
    <main>
    <%String username = (String) session.getAttribute("username"); %>
    <jsp:useBean id = "datos" class = "controller.ListarPreferenciasOfertasServlet"/>
      <div class="card text-white bg-info mb-3" style="max-width: 90rem; position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%);">
        <div class="card-header">Hola <%= username %>!</div>
        <div class="card-body">
          <h4 class="card-title">Ofertas:</h4>
          <table class="table table-hover">
			  <thead>
			    <tr>
			      <th scope="col">Nombre</th>
			      <th scope="col">Precio</th>
			      <th scope="col">Tipo</th>
			      <th scope="col"/>
			    </tr>
			  </thead>
			  <tbody>
			 	<c:forEach items="${list}" var="item">
				 	<tr class="table-primary">
				 		
						<th scope="row"><c:out value="${item}"></c:out> </th>
						<td><c:out value="${item.precio()}"></c:out> </td>
						<td><c:out value="${item}"></c:out></td>
						<td><a href="#" style="color: black;">Comprar</a></td>
				    </tr>
			 	</c:forEach>			    
			  </tbody>
		</table> 
		<c:forEach var="i" begin = "1" end = "10">
			Item <c:out value = "${i}"/><p>
		</c:forEach>
        </div>
      </div>
    </main>

    <footer style="color: gray;">
      <b>Secretaria de Turismo de la Tierra Media - 2021</b>
    </footer>

</body>
</html>