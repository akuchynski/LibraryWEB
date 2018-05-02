package by.htp.library.servlets.servlet;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.bean.User;
import by.htp.library.dao.EmployeeDao;
import by.htp.library.dao.UserDao;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		final AtomicReference<UserDao> userdao = (AtomicReference<UserDao>) request.getServletContext().getAttribute("userdao");
		final AtomicReference<EmployeeDao> employeedao = (AtomicReference<EmployeeDao>) request.getServletContext().getAttribute("employeedao");

		final Integer emplId = Integer.parseInt(request.getParameter("emplId"));
		
		final String login = request.getParameter("login");
		final String email = request.getParameter("email");
		final String password = request.getParameter("password");

		final User user = new User();

		user.setId(employeedao.get().read(emplId).getId());
		user.setLogin(login);
		user.setEmail(email);
		user.setPassword(password);
		
		userdao.get().create(user);

		response.sendRedirect(request.getContextPath() + "/");
		//doGet(request, response);
	}
}
