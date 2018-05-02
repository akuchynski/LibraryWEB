package by.htp.library.servlets.servlet;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.bean.Book;
import by.htp.library.bean.Employee;
import by.htp.library.bean.User;
import by.htp.library.dao.BookDao;
import by.htp.library.dao.EmployeeDao;
import by.htp.library.dao.UserDao;

public class UserAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final AtomicReference<BookDao> bookdao = (AtomicReference<BookDao>) request.getServletContext().getAttribute("bookdao");
		final AtomicReference<EmployeeDao> employeedao = (AtomicReference<EmployeeDao>) request.getServletContext().getAttribute("employeedao");
		
		final List<Book> bookList = bookdao.get().readAll();
		request.getSession().setAttribute("bookList", bookList);
		
		final List<Employee> employeeList = employeedao.get().readAll();
		request.getSession().setAttribute("employeeList", employeeList);
		
		String menuPath = (String)request.getSession().getAttribute("menuPath");
		
		request.getRequestDispatcher(menuPath + "/user-add.jsp").forward(request, response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final AtomicReference<UserDao> userdao = (AtomicReference<UserDao>) request.getServletContext().getAttribute("userdao");
		
		final Integer id = Integer.parseInt(request.getParameter("emplId"));
		final String login = request.getParameter("login");
		final String email = request.getParameter("email");
		final String password = request.getParameter("password");
		
		final User user = new User();
		
		user.setId(id);
		user.setLogin(login);
		user.setEmail(email);
		user.setPassword(password);
		
		userdao.get().create(user);

		doGet(request, response);
	}
}
