package by.htp.library.servlets.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.bean.Book;
import by.htp.library.bean.Employee;
import by.htp.library.bean.Order;
import by.htp.library.bean.User;
import by.htp.library.dao.BookDao;
import by.htp.library.dao.EmployeeDao;
import by.htp.library.dao.OrderDao;
import by.htp.library.dao.UserDao;

public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final AtomicReference<BookDao> bookDao = (AtomicReference<BookDao>) request.getServletContext().getAttribute("bookdao");
		final AtomicReference<EmployeeDao> employeeDao = (AtomicReference<EmployeeDao>) request.getServletContext().getAttribute("employeedao");
		final AtomicReference<UserDao> userDao = (AtomicReference<UserDao>) request.getServletContext().getAttribute("userdao");
		final AtomicReference<OrderDao> orderDao = (AtomicReference<OrderDao>) request.getServletContext().getAttribute("orderdao");
		
		final List<Book> bookList = bookDao.get().readAll();
		final List<Employee> employeeList = employeeDao.get().readAll();
		final List<User> userList = userDao.get().readAll();
		final List<Order> orderList = orderDao.get().readAll();
		final List<Order> orderListWait = orderDao.get().readOrdersByStatus("WAIT");
		
		request.getSession().setAttribute("bookCount", bookList.size());
		request.getSession().setAttribute("employeeCount", employeeList.size());
		request.getSession().setAttribute("userCount", userList.size());
		request.getSession().setAttribute("orderCount", orderList.size());
		
		Map<Integer, Book> bookMap = bookList.stream().collect(Collectors.toMap(Book::getId, item -> item));
		Map<Integer, Employee> employeeMap = employeeList.stream().collect(Collectors.toMap(Employee::getId, item -> item));
		Map<Integer, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, item -> item));
		
		request.getSession().setAttribute("orderListWait", orderListWait);
		request.getSession().setAttribute("userList", userList);
		request.getSession().setAttribute("bookMap", bookMap);
		request.getSession().setAttribute("employeeMap", employeeMap);
		request.getSession().setAttribute("userMap", userMap);
		
		String menuPath = (String)request.getSession().getAttribute("menuPath");
		
		request.getRequestDispatcher(menuPath + "/dashboard.jsp").forward(request, response);
	}
}
