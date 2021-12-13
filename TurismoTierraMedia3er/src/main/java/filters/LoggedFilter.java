package filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

@WebFilter(urlPatterns = "*.do")

public class LoggedFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String username = (String) ((HttpServletRequest)request).getSession().getAttribute("username");
		if (username != null) {			
			chain.doFilter(request, response);
		} else {    		
    		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/login.jsp");
      		dispatcher.forward(request, response);
		}
	}

}
