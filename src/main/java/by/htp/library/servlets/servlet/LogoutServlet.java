package by.htp.library.servlets.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {
private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Loguot servlet work");
		final HttpSession session = req.getSession();

		session.removeAttribute("password");
		session.removeAttribute("login");
		session.removeAttribute("role");

		resp.sendRedirect(req.getContextPath() + "/");
	}
}
