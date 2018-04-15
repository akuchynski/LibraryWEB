package by.htp.library.servlets.filter;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.library.bean.ROLE;
import by.htp.library.dao.UserDao;

import static java.util.Objects.nonNull;

//@WebFilter("/")
public class AuthFilter implements Filter {

	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("AuthFilter init");
	}

	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {

		System.out.println("AuthFilter work");
		
		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse res = (HttpServletResponse) response;

		final String login = req.getParameter("login");
		final String password = req.getParameter("password");

		@SuppressWarnings("unchecked")
		final AtomicReference<UserDao> userdao = (AtomicReference<UserDao>) req.getServletContext()
				.getAttribute("userdao");

		final HttpSession session = req.getSession();

		// Logged user
		
		if (nonNull(session) && nonNull(session.getAttribute("login")) && nonNull(session.getAttribute("password"))) {
			String path = ((HttpServletRequest) req).getRequestURI();
			if (path.contains("logout") || path.contains("assets") || path.contains("book-list") || path.contains("book-add")){
			    chain.doFilter(req, res);
			} else {
				final ROLE role = (ROLE) session.getAttribute("role");
				moveToMenu(req, res, role);
			}

		} else if (userdao.get().userIsExist(login, password)) {

			final ROLE role = userdao.get().getRoleByLoginPassword(login, password);

			req.getSession().setAttribute("password", password);
			req.getSession().setAttribute("login", login);
			req.getSession().setAttribute("role", role);

			moveToMenu(req, res, role);

		} else {

			moveToMenu(req, res, ROLE.UNKNOWN);
		}

	}

	/**
	 * Move user to menu.
	 * If access 'administrator' move to administrator menu.
	 * If access 'user' move to user menu.
	 */

	private void moveToMenu(final HttpServletRequest req, final HttpServletResponse res, final ROLE role)
			throws ServletException, IOException {

		if (role.equals(ROLE.ADMINISTRATOR)) {

			req.getRequestDispatcher("/jsp/admin_menu.jsp").forward(req, res);

		} else if (role.equals(ROLE.USER)) {

			req.getRequestDispatcher("/jsp/user_menu.jsp").forward(req, res);

		} else {

			req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
		}
	}

	public void destroy() {

	}
}
