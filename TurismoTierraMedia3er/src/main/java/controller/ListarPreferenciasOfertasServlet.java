package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Ofertable;
import model.Usuario;
import model.OfertasUsuario;
import persistence.commons.DAOFactory;

import service.OfertasService;

@WebServlet("/preferencia.do")
public class ListarPreferenciasOfertasServlet extends HttpServlet {
	private static final long serialVersionUID = 5236763640203028665L;
	OfertasService servOfertas = new OfertasService();

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String)((HttpServletRequest) request).getSession().getAttribute("username");
		Usuario user = DAOFactory.getUsuarioDAO().findByUsername(username);
		List<Ofertable> ofertas = servOfertas.list(user.getPreferencia());
		List<OfertasUsuario> salida = new ArrayList<OfertasUsuario>();
		for(Ofertable ofert: ofertas) {
			salida.add(new OfertasUsuario(ofert));
		}
		request.setAttribute("list",salida);
		getServletContext().getRequestDispatcher("/user.jsp").forward(request, response);
	}

}
