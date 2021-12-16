package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Ofertable;
import persistence.commons.DAOFactory;
import service.OfertasService;

/**
 * Servlet implementation class AdminOfertasServlet
 */
@WebServlet("/admofertas.adm")
public class AdminOfertasServlet extends HttpServlet {
	private static final long serialVersionUID = -6026026092254088313L;
	
	OfertasService ofertaService;
	
	public void init() throws ServletException {
		super.init();
		ofertaService = new OfertasService();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int opcion = (int)request.getAttribute("Opcion");
		switch (opcion) {
		case 1:
			request.setAttribute("Opcion",null);
			//insertar Atraccion
			//ofertaService.insert(nombre, precio, tiempo, cupo, tipo);
			break;
		case 2:
			request.setAttribute("Opcion",null);
			//insertar promocion
			//ofertaService.insert(nombre, conformacion, tipo, tipoPromocion, especial);
			break;
		case 3:
			request.setAttribute("Opcion",null);
			//editar promocion
			//Ofertable editada = DAOFactory.getOfertableDAO().find(nombre);
			/*
			 * aca irian los datos a editar
			 */
			//ofertaService.update(editada);
			break;
		case 4:
			request.setAttribute("Opcion",null);
			//editar atraccion
			//Ofertable editada = DAOFactory.getOfertableDAO().find(nombre);
			/*
			 * aca irian los datos a editar
			 */
			//ofertaService.update(editada);
			request.setAttribute("Opcion",null);
			break;
		case 5:
			request.setAttribute("Opcion",null);
			ofertaService.delete( (String)request.getAttribute("Oferta") );
			break;
		default:
			break;
		}
	}
}
