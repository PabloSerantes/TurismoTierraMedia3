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
import service.ProductoService;
/**
 * Servlet implementation class ListarOfertasServlet
 */
@WebServlet("/ofertas.do")
public class ListarOfertasServlet extends HttpServlet {
	private static final long serialVersionUID = -7004345150564559536L;
	OfertasService servOfertas;
	
	public void init() throws ServletException {
		super.init();
		servOfertas = new OfertasService();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = (String)((HttpServletRequest) request).getSession().getAttribute("username");
		String salida = "";
		Usuario user = DAOFactory.getUsuarioDAO().findByUsername(username);
		request.setAttribute("list",servOfertas.list());
		if(user.isAdmin()){
			salida = "/admin.jsp";
		} else {
			salida = "/user.jsp";
		}
		getServletContext().getRequestDispatcher(salida).forward(request, response);
	}
}