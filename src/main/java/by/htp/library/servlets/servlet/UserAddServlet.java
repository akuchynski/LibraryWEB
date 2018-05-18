package by.htp.library.servlets.servlet;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.library.bean.Book;
import by.htp.library.bean.Employee;
import by.htp.library.bean.User;
import by.htp.library.dao.BookDao;
import by.htp.library.dao.EmployeeDao;
import by.htp.library.dao.UserDao;

public class UserAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AtomicReference<BookDao> bookdao = (AtomicReference<BookDao>) request.getServletContext()
				.getAttribute("bookdao");
		AtomicReference<EmployeeDao> employeedao = (AtomicReference<EmployeeDao>) request.getServletContext()
				.getAttribute("employeedao");

		List<Book> bookList = bookdao.get().readAll();
		request.getSession().setAttribute("bookList", bookList);

		List<Employee> employeeList = employeedao.get().readAll();
		request.getSession().setAttribute("employeeList", employeeList);

		String menuPath = (String) request.getSession().getAttribute("menuPath");

		request.getRequestDispatcher(menuPath + "/user-add.jsp").forward(request, response);

		request.getSession().setAttribute("successUserSubmit", false);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AtomicReference<UserDao> userdao = (AtomicReference<UserDao>) request.getServletContext()
				.getAttribute("userdao");
		HttpSession session = request.getSession();
		User user = new User();

		user.setId(Integer.parseInt(request.getParameter("emplId")));
		user.setLogin(request.getParameter("login"));
		user.setEmail(request.getParameter("email"));
		user.setPassword(request.getParameter("password"));

		userdao.get().create(user);

		session.setAttribute("successUserSubmit", true);

		response.sendRedirect("user-add");
	}
}
