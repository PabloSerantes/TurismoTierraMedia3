package controller;
import java.io.IOException;
import java.util.Objects;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admpipe.adm")
public class AdminPipeServlet extends HttpServlet {

	private static final long serialVersionUID = 4240043233939222241L;

	public void init() throws ServletException {
		super.init();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if( ((int) request.getAttribute("adminclean")) == 1) {
			request.setAttribute("listaUsuarios", null);
			request.setAttribute("listaOfertas", null);
			request.setAttribute("listaItinerarios", null);
			request.setAttribute("Opcion", null);
		}
		if( Objects.isNull( request.getAttribute("listaUsuarios") ) ) {
			request.setAttribute("Opcion", 1);
			getServletContext().getRequestDispatcher("/users.adm").forward(request, response);
		}
		if (Objects.isNull( request.getAttribute("listaOfertas") ) ) {
			getServletContext().getRequestDispatcher("/ofertas.do").forward(request, response);	
		}
		if (Objects.isNull( request.getAttribute("listaItinerarios") ) ) {
			getServletContext().getRequestDispatcher("/itinerarios.do").forward(request, response);
		}
		
		getServletContext().getRequestDispatcher("/admin.jsp").forward(request, response);
		
	}

}
