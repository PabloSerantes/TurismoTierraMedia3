package controller;

import java.io.IOException;
import java.util.Objects;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.TokensService;

@WebServlet("/SesionCheck")
public class TokenServlet extends HttpServlet {
	private static final long serialVersionUID = 8968865393396611058L;
	TokensService tokenService;
	
	public void init() throws ServletException {
		super.init();
		tokenService = new TokensService();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("respuestaTokent",null);
		String username = (String)((HttpServletRequest) request).getSession().getAttribute("username");
		int opcion = (int)request.getAttribute("Opcion");
		Cookie[] cookies = request.getCookies();
		if(Objects.nonNull(username) && Objects.nonNull(opcion)) {
			switch (opcion) {
				case 1:
					String usr="";
					String tkn="";
					for (Cookie cook : cookies) {
						if(cook.getName().equals("TokenDeSesion")) {
							tkn = cook.getValue();
						}
						
						if(cook.getName().equals("username")) {
							usr = cook.getValue();
						}
					}
					if(usr.isBlank() || tkn.isBlank()) {
						request.setAttribute("respuestaTokent",false);
					} else {
						boolean salida = tokenService.validar(usr, tkn);
						if(salida) {
							request.setAttribute("respuestaTokent",true);
						} else {
							for (Cookie cook : cookies) {
								if(cook.getName().equals("TokenDeSesion")||cook.getName().equals("username")) {
									cook.setMaxAge(0);
									response.addCookie(cook);
								}
							}
							request.setAttribute("respuestaTokent",false);
						}
					}
					
					break;
					
				case 2:
					if(tokenService.tokearUsuario(username)) {
						Cookie ctok = new Cookie("TokenDeSesion", tokenService.ObtenerToken(username));
						Cookie cusr = new Cookie("username", username);
						ctok.setMaxAge(518400);
						cusr.setMaxAge(518400);
						response.addCookie(cusr);
						response.addCookie(ctok);
						request.setAttribute("respuestaTokent",true);
					}
					break;
					
				case 3:
					if(tokenService.delete(username)) {
						for (Cookie cook : cookies) {
							if(cook.getName().equals("TokenDeSesion")||cook.getName().equals("username")) {
								cook.setMaxAge(0);
								response.addCookie(cook);
							}
						}
						request.setAttribute("respuestaTokent",false);
					}
					break;
					
				default:
					break;
			}
		}
		String urlVieja = (String)request.getAttribute("urlVieja");
		request.setAttribute("urlVieja", null);
		getServletContext().getRequestDispatcher(urlVieja).forward(request, response);
	}

}
