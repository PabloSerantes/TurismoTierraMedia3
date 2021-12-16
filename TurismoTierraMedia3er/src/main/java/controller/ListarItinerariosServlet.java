package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Itinerarios;
import model.Usuario;
import persistence.commons.DAOFactory;
import service.ItinerariosService;

@WebServlet("/itinerarios.do")
public class ListarItinerariosServlet extends HttpServlet {
	private static final long serialVersionUID = 5572452166775641489L;
	ItinerariosService itinerariosService;
	
	public void init() throws ServletException {
		super.init();
		itinerariosService = new ItinerariosService();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String)((HttpServletRequest) request).getSession().getAttribute("username");
		Usuario user = DAOFactory.getUsuarioDAO().findByUsername(username);
		
		if(user.isAdmin()) {
			
			List<Itinerarios> compras = itinerariosService.listAll();
			request.setAttribute("listaItinerarios",compras);
			getServletContext().getRequestDispatcher("/admpipe.adm").forward(request, response);
			
		} else {
			
			List<Itinerarios> compras = itinerariosService.findByUsername(username);
			request.setAttribute("list",compras);
			getServletContext().getRequestDispatcher("/???").forward(request, response);
			
		}
	}

}
