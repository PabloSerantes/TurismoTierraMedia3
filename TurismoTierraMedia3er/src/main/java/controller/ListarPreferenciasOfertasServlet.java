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

@WebServlet("/preferencia.do")
public class ListarPreferenciasOfertasServlet extends HttpServlet {
	private static final long serialVersionUID = 5236763640203028665L;
	OfertasService servOfertas;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> tipos = new ArrayList<String>();
		tipos.add("placeholder");
		tipos.add("Aventura");
		tipos.add("Paisaje");
		tipos.add("Degustacion");
		String username = (String)((HttpServletRequest) request).getSession().getAttribute("user");
		Usuario user = DAOFactory.getUsuarioDAO().findByUsername(username);
		request.setAttribute("list",servOfertas.list(user.getPreferencia()));
		request.setAttribute("tipos", tipos);
		getServletContext().getRequestDispatcher("user.jsp").forward(request, response);
	}

}
