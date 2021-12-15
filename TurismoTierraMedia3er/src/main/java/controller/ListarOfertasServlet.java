package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;
import persistence.commons.DAOFactory;
import service.OfertasService;
/**
 * Servlet implementation class ListarOfertasServlet
 */
@WebServlet("/ofertas.do")
public class ListarOfertasServlet extends HttpServlet {
	private static final long serialVersionUID = -7004345150564559536L;
	OfertasService servOfertas;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> tipos = new ArrayList<String>();
		tipos.add("placeholder");
		tipos.add("Aventura");
		tipos.add("Paisaje");
		tipos.add("Degustacion");
		String username = (String)((HttpServletRequest) request).getSession().getAttribute("user");
		String salida = "";
		Usuario user = DAOFactory.getUsuarioDAO().findByUsername(username);
		request.setAttribute("tipos", tipos);
		request.setAttribute("list",servOfertas.list());
		if(user.isAdmin()){
			salida = "admin.jsp";
		} else {
			salida = "usergeneral.jsp";
		}
		getServletContext().getRequestDispatcher(salida).forward(request, response);
	}
}
