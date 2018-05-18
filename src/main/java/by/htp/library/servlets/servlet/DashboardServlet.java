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
import javax.servlet.http.HttpSession;

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
	private static final int LAST_USERS_COUNT = 3;
	private static final int LAST_BOOKS_COUNT = 5;
	private static final int LAST_USERS_ORDERS_COUNT = 5;
	private static final String ORDERS_ADMIN_STATUS = "WAIT";

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AtomicReference<BookDao> bookDao = (AtomicReference<BookDao>) request.getServletContext()
				.getAttribute("bookdao");
		AtomicReference<EmployeeDao> employeeDao = (AtomicReference<EmployeeDao>) request.getServletContext()
				.getAttribute("employeedao");
		AtomicReference<UserDao> userDao = (AtomicReference<UserDao>) request.getServletContext()
				.getAttribute("userdao");
		AtomicReference<OrderDao> orderDao = (AtomicReference<OrderDao>) request.getServletContext()
				.getAttribute("orderdao");

		HttpSession session = request.getSession();
		List<Book> bookList = bookDao.get().readAll();
		List<Employee> employeeList = employeeDao.get().readAll();
		List<User> userList = userDao.get().readAll();
		List<User> userLastList = userDao.get().readLastUsers(LAST_USERS_COUNT);
		List<Book> bookLastList = bookDao.get().readLastBooks(LAST_BOOKS_COUNT);

		List<Order> orderList = orderDao.get().readAll();
		List<Order> orderListWait = orderDao.get().readOrdersByStatus(ORDERS_ADMIN_STATUS);

		int emplId = (Integer) session.getAttribute("currentId");
		List<Order> orderLastList = orderDao.get().readLastOrdersByEmployeeId(emplId, LAST_USERS_ORDERS_COUNT);

		session.setAttribute("bookCount", bookList.size());
		session.setAttribute("employeeCount", employeeList.size());
		session.setAttribute("userCount", userList.size());
		session.setAttribute("orderCount", orderList.size());

		Map<Integer, Book> bookMap = bookList.stream().collect(Collectors.toMap(Book::getId, item -> item));
		Map<Integer, Employee> employeeMap = employeeList.stream()
				.collect(Collectors.toMap(Employee::getId, item -> item));
		Map<Integer, User> userMap = userLastList.stream().collect(Collectors.toMap(User::getId, item -> item));

		session.setAttribute("orderListWait", orderListWait);
		session.setAttribute("userLastList", userLastList);
		session.setAttribute("bookLastList", bookLastList);
		session.setAttribute("orderLastList", orderLastList);
		session.setAttribute("bookMap", bookMap);
		session.setAttribute("employeeMap", employeeMap);
		session.setAttribute("userMap", userMap);

		String menuPath = (String) session.getAttribute("menuPath");
		request.getRequestDispatcher(menuPath + "/dashboard.jsp").forward(request, response);
	}
}
