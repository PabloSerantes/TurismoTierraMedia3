package filters;

import java.io.IOException;
import java.util.Objects;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;


import model.Usuario;
import persistence.commons.DAOFactory;

@WebFilter(urlPatterns = "*.adm")

public class IsAdminFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		Usuario user = DAOFactory.getUsuarioDAO().findByUsername((String)((HttpServletRequest) request).getSession().getAttribute("user"));

		if (Objects.nonNull(user) && user.isAdmin()) {
			chain.doFilter(request, response);
		} else {
			request.setAttribute("flash", "No tienes los permisos necesarios");
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/user.jsp");
			dispatcher.forward(request, response);
		}

	}

}
