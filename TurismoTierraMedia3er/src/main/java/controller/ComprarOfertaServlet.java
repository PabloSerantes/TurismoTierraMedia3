package controller;

import java.io.IOException;
import java.util.Objects;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Ofertable;
import model.Usuario;
import persistence.commons.DAOFactory;
import service.ItinerariosService;

@WebServlet("/comprar.do")
public class ComprarOfertaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ItinerariosService itinerariosService;
	
	public void init() throws ServletException {
		super.init();
		itinerariosService = new ItinerariosService();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String)((HttpServletRequest) request).getSession().getAttribute("username");
		Usuario user = DAOFactory.getUsuarioDAO().findByUsername(username);
		Ofertable compra = DAOFactory.getOfertableDAO().find(request.getParameter("compra"));
		if(Objects.nonNull(compra)&&Objects.nonNull(user)) {
			if(user.puedeComprar(compra)) {
				itinerariosService.insert(compra, username);
				getServletContext().getRequestDispatcher("/???").forward(request, response);
			}
		}
	}

}
