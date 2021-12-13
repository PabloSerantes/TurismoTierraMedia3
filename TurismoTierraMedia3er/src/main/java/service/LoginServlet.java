package service;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/logintry")

public class LoginServlet extends HttpServlet {
       
	private static final long serialVersionUID = -1267936749008845670L;

	@Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	String username = req.getParameter("username");
    	String password = req.getParameter("password");
    	   	
    	if (username.equals("ricardo") && password.equals("ruben")) {
    		req.getSession().setAttribute("username", "ricardo");
    		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/user.jsp");
    		dispatcher.forward(req, resp);
    	} else {
    		req.setAttribute("flash", "Error, usuario o contraseña incorrectos");    		
    		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
      		dispatcher.forward(req, resp);
    	}
    }
}
