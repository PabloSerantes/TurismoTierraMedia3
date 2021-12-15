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

@WebFilter(urlPatterns = "*.do")

public class LoggedFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String username = (String) ((HttpServletRequest)request).getSession().getAttribute("username");
		Usuario user = DAOFactory.getUsuarioDAO().findByUsername(username);
		if (Objects.nonNull(user)) {			
			chain.doFilter(request, response);
		} else {    		
    		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/login.jsp");
      		dispatcher.forward(request, response);
		}
	}

}
