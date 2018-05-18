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
import by.htp.library.dao.BookDao;
import by.htp.library.dao.EmployeeDao;
import by.htp.library.dao.OrderDao;

public class OrderListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AtomicReference<OrderDao> orderdao = (AtomicReference<OrderDao>) request.getServletContext()
				.getAttribute("orderdao");
		AtomicReference<BookDao> bookdao = (AtomicReference<BookDao>) request.getServletContext()
				.getAttribute("bookdao");
		AtomicReference<EmployeeDao> employeedao = (AtomicReference<EmployeeDao>) request.getServletContext()
				.getAttribute("employeedao");
		HttpSession session = request.getSession();

		List<Order> orderListEmpl = orderdao.get()
				.readOrdersByEmployeeId((Integer) session.getAttribute("currentId"));
		List<Order> orderListAll = orderdao.get().readAll();

		List<Book> bookList = bookdao.get().readAll();
		List<Employee> employeeList = employeedao.get().readAll();

		Map<Integer, Book> bookMap = bookList.stream().collect(Collectors.toMap(Book::getId, item -> item));
		Map<Integer, Employee> employeeMap = employeeList.stream()
				.collect(Collectors.toMap(Employee::getId, item -> item));

		request.getSession().setAttribute("orderListEmpl", orderListEmpl);
		request.getSession().setAttribute("orderListAll", orderListAll);
		request.getSession().setAttribute("bookMap", bookMap);
		request.getSession().setAttribute("employeeMap", employeeMap);

		String menuPath = (String) request.getSession().getAttribute("menuPath");

		request.getRequestDispatcher(menuPath + "/order-list.jsp").forward(request, response);
		request.getSession().removeAttribute("messageClass");
	}
}
