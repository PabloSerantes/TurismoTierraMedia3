package controller;

import java.io.IOException;
import java.util.Objects;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UsuariosService;

/**
 * Servlet implementation class AdminUsuariosServlet
 */
@WebServlet("/users.adm")
public class AdminUsuariosServlet extends HttpServlet {
	private static final long serialVersionUID = 52252650761756902L;
	UsuariosService usuariosService;

	public void init() throws ServletException {
		super.init();
		usuariosService = new UsuariosService();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String sopcion = "";
		if(Objects.nonNull(request.getParameter("SOpcion"))) {
			sopcion = (String) request.getParameter("SOpcion");
		}
		int opcion = 0;
		if(Objects.nonNull(request.getAttribute("Opcion"))) {
			opcion = (int)request.getAttribute("Opcion");
		}
		if(!sopcion.isBlank()) {
			opcion = Integer.parseInt(sopcion);
		}
		switch (opcion) {
		case 1:
			request.setAttribute("Opcion",null);
			request.setAttribute("listaUsuarios",usuariosService.findAll());
			break;
		case 2:
			request.setAttribute("Opcion",null);
			//a completar con pagina lista
			//usuariosService.insert(username, opcion, opcion, null, null, username)
			break;
		case 3:
			request.setAttribute("Opcion",null);
			String nombre = (String)request.getParameter("nombreBorrar");
			usuariosService.delete(nombre);
			break;
		default:
			break;
		}
		getServletContext().getRequestDispatcher("/admpipe.adm?nombreBorrar=&SOpcion=").forward(request, response);

	}
	
}
