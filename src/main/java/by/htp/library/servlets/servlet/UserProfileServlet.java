package by.htp.library.servlets.servlet;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.library.bean.Employee;
import by.htp.library.bean.User;
import by.htp.library.dao.EmployeeDao;
import by.htp.library.dao.UserDao;

public class UserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String menuPath = (String)request.getSession().getAttribute("menuPath");
		
		request.getRequestDispatcher(menuPath + "/user-profile.jsp").forward(request, response);
		
		request.getSession().setAttribute("successProfileUpdate", false);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final AtomicReference<EmployeeDao> employeedao = (AtomicReference<EmployeeDao>) request.getServletContext().getAttribute("employeedao");
		final AtomicReference<UserDao> userdao = (AtomicReference<UserDao>) request.getServletContext().getAttribute("userdao");
		
		final HttpSession session = request.getSession();
		final User user = new User();
		final Employee employee = new Employee();
		final int id = (Integer)session.getAttribute("currentId");
		
		user.setEmail(request.getParameter("email"));
		user.setPassword(request.getParameter("new_password"));
		
		employee.setName(request.getParameter("name"));
		employee.setSurname(request.getParameter("surname"));
		employee.setYear(Integer.parseInt(request.getParameter("year")));
		
		userdao.get().update(id, user);
		employeedao.get().update(id, employee);
	
		session.setAttribute("successProfileUpdate", true);

		response.sendRedirect("user-profile");
	}
}
