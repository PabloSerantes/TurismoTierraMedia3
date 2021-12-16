package controller;

import java.io.IOException;

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
	private static final long serialVersionUID = 7666630909800556326L;
	UsuariosService usuariosService;

	public void init() throws ServletException {
		super.init();
		usuariosService = new UsuariosService();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int opcion = (int)request.getAttribute("Opcion");
		switch (opcion) {
		case 1:
			request.setAttribute("Opcion",null);
			request.setAttribute("listaUsuarios",usuariosService.findAll());
			getServletContext().getRequestDispatcher("/admpipe.adm").forward(request, response);
			break;
		case 2:
			request.setAttribute("Opcion",null);
			//a completar con pagina lista
			//usuariosService.insert(username, opcion, opcion, null, null, username)
			break;
		case 3:
			request.setAttribute("Opcion",null);
			usuariosService.delete((String)request.getAttribute("nombreBorrar"));
			break;
		default:
			break;
		}
	}

}
