package controller;

import java.io.IOException;
import java.util.Objects;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;
import persistence.commons.DAOFactory;

@WebServlet("/logintry")

public class LoginServlet extends HttpServlet {
       
	private static final long serialVersionUID = -1267936749008845670L;

	@Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	String username = req.getParameter("username");
    	String password = req.getParameter("password");
    	Usuario user = DAOFactory.getUsuarioDAO().findByUsername(username);
    	if (Objects.nonNull(user)) {
			if(user.auth(password)){
				req.getSession().setAttribute("username", "ricardo");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/user.jsp");
				dispatcher.forward(req, resp);
			} else {
				req.setAttribute("flash", "Error, usuario o contraseña incorrectos");    		
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
				dispatcher.forward(req, resp);
			}
    	} else {
    		req.setAttribute("flash", "Error, usuario o contraseña incorrectos");    		
    		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
      		dispatcher.forward(req, resp);
    	}
    }
}