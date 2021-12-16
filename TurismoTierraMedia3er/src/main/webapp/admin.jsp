<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Administrador</title>
<link rel="stylesheet" href="bootstrap.min.css">
<link rel="stylesheet" href="stylesheet.css">
<script src="bootstrap.min.js" defer></script>
</head>
<body class="admin-body">
	<header>
		<div class="navbar navbar-expand-lg navbar-dark bg-primary">
			<div class="container-fluid">
				<a rel="stylesheet" href="user.html"><img class="navbar-brand"
					src="logoadmin.png" style="width: 80px; height: 90px;" alt="logo" /></a>
				<a href="logout" style="color: black; float: right;">Cerrar
					Sesión <img src="logout.png" style="width: 40px; height: 30px;"
					alt="Logout">
				</a>
			</div>
		</div>
	</header>

	<main>
		<nav class="accordion"id="tabSelector" >
			<div class="list-group">
				<div class="list-group-item list-group-item-action active" style="width:455px">Herramientas
					de Administrador</div>
			</div>
			<ul class="nav nav-tabs" style="width:455px">
				<li class="nav-item"><a class="nav-link active" data-bs-toggle="tab"
					href="#usuarios">Usuarios</a>
				</li>
				<li class="nav-item"><a class="nav-link" data-bs-toggle="tab" 
					href="#atracciones">Atracciones</a>
				</li>
				<li class="nav-item"><a class="nav-link" data-bs-toggle="tab" 
					href="#promociones">Promociones</a>
				</li>
				<li class="nav-item"><a class="nav-link" data-bs-toggle="tab" 
					href="#compras">Compras</a>
				</li>
			</ul>
		</nav>	
		<nav class="accordion" id="tabTable">			
			<div id="myTabContent" class="tab-content" style="overflow:auto; height:15em;">
				<div class="tab-pane fade active show" id="usuarios">
					<table class="table table-primary">
						  <thead>
						    <tr>
						      <th scope="col">Nombre</th>
						      <th scope="col">Presupuesto</th>
						      <th scope="col">Tiempo</th>
						      <th scope="col">Admin</th>
						      <th scope="col">Activo</th>
						      <th scope="col" colspan="2"/>
						    </tr>
						  </thead>
						  <tbody>
						  		<c:forEach items="${listaUsuarios}" var="item">
								 	<tr class="table-secondary">
								      <th scope="row"><c:out value="${item.getUsername()}"></c:out></th>
								      <td><c:out value="${item.getpresupuesto()}"></c:out></td>
								      <td><c:out value="${item.getTiempo()}"></c:out></td>
								      <td><c:out value="${item.isAdmin()}"></c:out></td>
								      <td><c:out value="${item.isActive()}"></c:out></td>
								      <td><a href="#" style="color: black;">Modificar</a></td>
								      <td>
								      <form action="users.adm" method="post">
									      	<input type="hidden" name="SOpcion" value="3">
									      	<input type="hidden" name="nombreBorrar" value="${item.getUsername()}">
	    									<button type="submit" class="btn-link">eliminar</button>
									  </form>
									  </td>
								    </tr>
								</c:forEach>	   
						  </tbody>
					</table>
				</div>
				
				
				<div class="tab-pane fade" id="atracciones">
					<table class="table table-primary">
						  <thead>
						    <tr>
						      <th scope="col">Nombre</th>
						      <th scope="col">Precio</th>
						      <th scope="col">Tiempo</th>
						      <th scope="col">active</th>
						      <th scope="col" colspan="2"/>
						    </tr>
						  </thead>
						  <tbody>
						  		<c:forEach items="${listaAtracciones}" var="item">
								 	<tr class="table-secondary">
								      <th scope="row"><c:out value="${item.getNombre()}"></c:out></th>
								      <td><c:out value="${item.getPrecio()}"></c:out></td>
								      <td><c:out value="${item.getTiempo()}"></c:out></td>
								      <td><c:out value="${item.isActive()}"></c:out></td>
								      <td><a href="#" style="color: black;">Modificar</a></td>
								      <td><a href="#" style="color: black;">Eliminar</a></td>
								    </tr>
							    </c:forEach>			    
						  </tbody>
					</table>
				</div>
				
				
				<div class="tab-pane fade" id="promociones">
					<table class="table table-primary">
						  <thead>
						    <tr>
						      <th scope="col">Nombre</th>
						      <th scope="col">Precio</th>
						      <th scope="col">Tiempo</th>
						      <th scope="col">Activo</th>
						      <th scope="col" colspan="2"/>
						    </tr>	
						  </thead>
						  <tbody>
							 	<c:forEach items="${listaPromociones}" var="item">
								 	<tr class="table-secondary">
								      <th scope="row"><c:out value="${item.getNombre()}"></c:out></th>
								      <td><c:out value="${item.getPrecio()}"></c:out></td>
								      <td><c:out value="${item.getTiempo()}"></c:out></td>
								      <td><c:out value="${item.isActive()}"></c:out></td>
								      <td><a href="#" style="color: black;">Modificar</a></td>
								      <td><a href="#" style="color: black;">Eliminar</a></td>
								    </tr>
							    </c:forEach>		    
						  </tbody>
					</table>
				</div>
				
				
				<div class="tab-pane fade" id="compras">
					<table class="table table-primary">
						  <thead>
						    <tr>
						    	<th scope="col">Usuario</th>
								<th scope="col">Nombre</th>
								<th scope="col">Abonado</th>
								<th scope="col">Tiempo</th>
						    </tr>
						  </thead>
						  <tbody>
						  		<c:forEach items="${listaItinerarios}" var="item">
								 	<tr class="table-secondary">
								      <th scope="row"><c:out value="${item.getUsuario()}"></c:out></th>
								      <td><c:out value="${item.getNombre()}"></c:out></td>
								      <td><c:out value="${item.getPrecio()}"></c:out></td>
								      <td><c:out value="${item.getTiempo()}"></c:out></td>
								    </tr>
								</c:forEach>			    
						  </tbody>
					</table>
				</div>
			</div>
		</nav>
	</main>

	<footer>
		<b>Secretaría de Turismo de la Tierra Media © 2021</b>
	</footer>
</body>
</html>