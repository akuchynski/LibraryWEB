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

import by.htp.library.bean.Employee;
import by.htp.library.bean.ROLE;
import by.htp.library.bean.User;
import by.htp.library.dao.EmployeeDao;
import by.htp.library.dao.UserDao;

import static java.util.Objects.nonNull;

public class AuthFilter implements Filter {

	public void init(FilterConfig fConfig) throws ServletException {
	}

	@SuppressWarnings("unchecked")
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		
	    request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		
		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse res = (HttpServletResponse) response;

		final String login = req.getParameter("login");
		final String password = req.getParameter("password");

		final AtomicReference<UserDao> userdao = (AtomicReference<UserDao>) req.getServletContext().getAttribute("userdao");
		final AtomicReference<EmployeeDao> employeedao = (AtomicReference<EmployeeDao>) request.getServletContext().getAttribute("employeedao");
		final HttpSession session = req.getSession();

		// Logged user

		String path = ((HttpServletRequest) req).getRequestURI();
		
		if (nonNull(session) && nonNull(session.getAttribute("login")) && nonNull(session.getAttribute("password"))) {
			
			if (path.equals(req.getContextPath() + "/")) {
				
				final ROLE role = (ROLE) session.getAttribute("role");
				moveToMenu(req, res, role);
				
			} else {
				
				chain.doFilter(req, res);
			}
			
		// Not logged user
		
		} else if (userdao.get().userIsExist(login, password)) {

			final ROLE role = userdao.get().getRoleByLoginPassword(login, password);
			int currentId = userdao.get().readByLogin(login).getId();

			req.getSession().setAttribute("password", password);
			req.getSession().setAttribute("login", login);
			req.getSession().setAttribute("role", role);
			req.getSession().setAttribute("currentId", currentId);
			
			Employee currentEmployee = employeedao.get().read(currentId);
			User currentUser = userdao.get().read(currentId);
			
			req.getSession().setAttribute("currentEmployee", currentEmployee);
			req.getSession().setAttribute("currentUser", currentUser);

			moveToMenu(req, res, role);
			
		} else if (path.contains("assets") || path.contains("login") || path.contains("register") || path.contains("ajax")) {

			chain.doFilter(req, res);
			
		} else {
			
			moveToMenu(req, res, ROLE.UNKNOWN);
		}
	}

	private void moveToMenu(final HttpServletRequest req, final HttpServletResponse res, final ROLE role)
			throws ServletException, IOException {

		if (role.equals(ROLE.ADMINISTRATOR)) {

			req.getSession().setAttribute("menuPath", "/jsp/admin");
			res.sendRedirect("dashboard");

		} else if (role.equals(ROLE.USER)) {
			
			req.getSession().setAttribute("menuPath", "/jsp/user");
			res.sendRedirect("dashboard");

		} else {

			res.sendRedirect("login");
		}
	}

	public void destroy() {

	}
}
