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
import by.htp.library.dao.BookDao;
import by.htp.library.dao.EmployeeDao;
import by.htp.library.dao.OrderDao;

public class OrderListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final AtomicReference<OrderDao> orderdao = (AtomicReference<OrderDao>) request.getServletContext().getAttribute("orderdao");
		final AtomicReference<BookDao> bookdao = (AtomicReference<BookDao>) request.getServletContext().getAttribute("bookdao");
		final AtomicReference<EmployeeDao> employeedao = (AtomicReference<EmployeeDao>) request.getServletContext().getAttribute("employeedao");
		
		final List<Order> orderList = orderdao.get().readAll();
		final List<Book> bookList = bookdao.get().readAll();
		final List<Employee> employeeList = employeedao.get().readAll();
		
		Map<Integer, Book> bookMap = bookList.stream().collect(Collectors.toMap(Book::getId, item -> item));
		Map<Integer, Employee> employeeMap = employeeList.stream().collect(Collectors.toMap(Employee::getId, item -> item));
		
		request.getSession().setAttribute("orderList", orderList);
		request.getSession().setAttribute("bookMap", bookMap);
		request.getSession().setAttribute("employeeMap", employeeMap);
		
		String menuPath = (String)request.getSession().getAttribute("menuPath");
		
		request.getRequestDispatcher(menuPath + "/order-list.jsp").forward(request, response);
	}
}