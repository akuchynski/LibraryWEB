package by.htp.library.servlets.filter;

import java.io.File;
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

	private static final String AVATAR_DIR = "assets\\images\\users\\";

	public void init(FilterConfig fConfig) throws ServletException {
	}

	@SuppressWarnings("unchecked")
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();

		String login = req.getParameter("login");
		String password = req.getParameter("password");

		AtomicReference<UserDao> userdao = (AtomicReference<UserDao>) req.getServletContext().getAttribute("userdao");
		AtomicReference<EmployeeDao> employeedao = (AtomicReference<EmployeeDao>) request.getServletContext()
				.getAttribute("employeedao");

		String path = ((HttpServletRequest) req).getRequestURI();

		if (nonNull(session) && nonNull(session.getAttribute("login")) && nonNull(session.getAttribute("password"))) {

			if (path.equals(req.getContextPath() + "/")) {

				ROLE role = (ROLE) session.getAttribute("role");
				moveToMenu(req, res, role);

			} else {

				chain.doFilter(req, res);
			}

		} else if (userdao.get().userIsExist(login, password)) {

			ROLE role = userdao.get().getRoleByLoginPassword(login, password);
			int currentId = userdao.get().readByLogin(login).getId();

			session.setAttribute("password", password);
			session.setAttribute("login", login);
			session.setAttribute("role", role);
			session.setAttribute("currentId", currentId);

			Employee currentEmployee = employeedao.get().read(currentId);
			User currentUser = userdao.get().read(currentId);

			session.setAttribute("currentEmployee", currentEmployee);
			session.setAttribute("currentUser", currentUser);

			String appPath = request.getServletContext().getRealPath("");
			String avatarPath = appPath + AVATAR_DIR + "avatar" + currentId + ".jpg";
			File file = new File(avatarPath);
			
			if (file.exists() && file.isFile()) {
				session.setAttribute("avatarNumber", currentId);
			} else {
				session.setAttribute("avatarNumber", 0);
			}

			moveToMenu(req, res, role);

		} else if (path.contains("assets") || path.contains("login") || path.contains("register")
				|| path.contains("ajax")) {

			chain.doFilter(req, res);

		} else {

			moveToMenu(req, res, ROLE.UNKNOWN);
		}
	}

	private void moveToMenu(final HttpServletRequest req, final HttpServletResponse res, final ROLE role)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		
		if (role.equals(ROLE.ADMINISTRATOR)) {

			session.setAttribute("menuPath", "/jsp/admin");
			res.sendRedirect("dashboard");

		} else if (role.equals(ROLE.USER)) {

			session.setAttribute("menuPath", "/jsp/user");
			res.sendRedirect("dashboard");

		} else {

			res.sendRedirect("login");
		}
	}

	public void destroy() {

	}
}
