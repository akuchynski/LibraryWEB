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

public class AuthFilter implements Filter {

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		
	    request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		
		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse res = (HttpServletResponse) response;

		final String login = req.getParameter("login");
		final String password = req.getParameter("password");

		@SuppressWarnings("unchecked")
		final AtomicReference<UserDao> userdao = (AtomicReference<UserDao>) req.getServletContext()
				.getAttribute("userdao");

		final HttpSession session = req.getSession();

		// Logged user

		String path = ((HttpServletRequest) req).getRequestURI();
		
		if (nonNull(session) && nonNull(session.getAttribute("login")) && nonNull(session.getAttribute("password"))) {
			
			if (path.equals(req.getContextPath() + "/") || path.contains("index")) {
				
				final ROLE role = (ROLE) session.getAttribute("role");
				moveToMenu(req, res, role);
				
			} else {

				chain.doFilter(req, res);
			}
			
		} else if (userdao.get().userIsExist(login, password)) {

			final ROLE role = userdao.get().getRoleByLoginPassword(login, password);

			req.getSession().setAttribute("password", password);
			req.getSession().setAttribute("login", login);
			req.getSession().setAttribute("role", role);

			moveToMenu(req, res, role);
			
		} else if (path.contains("assets") || path.contains("register") || path.contains("ajax")) {

			chain.doFilter(req, res);
			
		} else {
			
			moveToMenu(req, res, ROLE.UNKNOWN);
		}
	}

	private void moveToMenu(final HttpServletRequest req, final HttpServletResponse res, final ROLE role)
			throws ServletException, IOException {

		if (role.equals(ROLE.ADMINISTRATOR)) {

			req.getSession().setAttribute("menuPath", "/jsp/admin");
			req.getRequestDispatcher("/jsp/admin/index.jsp").forward(req, res);

		} else if (role.equals(ROLE.USER)) {
			
			req.getSession().setAttribute("menuPath", "/jsp/user");
			req.getRequestDispatcher("/jsp/user/index.jsp").forward(req, res);

		} else {

			req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);

		}
	}

	public void destroy() {

	}
}
