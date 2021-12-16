package controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Ofertable;

@WebServlet("/admpipe.adm")
public class AdminPipeServlet extends HttpServlet {

	private static final long serialVersionUID = 4240043233939222241L;

	public void init() throws ServletException {
		super.init();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if( Objects.isNull( request.getAttribute("listaUsuarios") ) ) {
			request.setAttribute("Opcion", 1);
			getServletContext().getRequestDispatcher("/users.adm").forward(request, response);
		}else {
			if (Objects.isNull( request.getAttribute("listaOfertas") ) ) {
				getServletContext().getRequestDispatcher("/ofertas.do").forward(request, response);	
			} else {
				if (Objects.isNull( request.getAttribute("listaItinerarios") ) ) {
					getServletContext().getRequestDispatcher("/itinerarios.do").forward(request, response);
				} else {
					List<Ofertable> ofertas = (List<Ofertable>) request.getAttribute("listaOfertas");
					List<Ofertable> atracciones = new ArrayList<Ofertable>();
					List<Ofertable> promociones = new ArrayList<Ofertable>();
					for(Ofertable oft: ofertas) {
						if(oft.esPromocion()) {
							promociones.add(oft);
						} else {
							atracciones.add(oft);
						}
					}
					request.setAttribute("listaAtracciones",atracciones);
					request.setAttribute("listaPromociones",promociones);
					getServletContext().getRequestDispatcher("/admin.jsp").forward(request, response);
				}
			}
		}
	}
}
