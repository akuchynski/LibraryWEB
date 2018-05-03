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
import by.htp.library.bean.Order;
import by.htp.library.bean.ROLE;
import by.htp.library.bean.STATUS;
import by.htp.library.dao.BookDao;
import by.htp.library.dao.EmployeeDao;
import by.htp.library.dao.OrderDao;

public class OrderAddServlet extends HttpServlet {
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
		
		request.getRequestDispatcher(menuPath + "/order-add.jsp").forward(request, response);
		
		request.getSession().setAttribute("successOrderSubmit", false);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final AtomicReference<OrderDao> orderdao = (AtomicReference<OrderDao>) request.getServletContext().getAttribute("orderdao");
		final HttpSession session = request.getSession();
		final Order order = new Order();
		
		final ROLE role = (ROLE) session.getAttribute("role");
		STATUS status = STATUS.WAIT;
		if (role.equals(ROLE.ADMINISTRATOR)) {
			status = STATUS.DELIVERED;
		}
		
		order.setBookId(Integer.parseInt(request.getParameter("bookId")));
		order.setEmplId(Integer.parseInt(request.getParameter("emplId")));
		order.setDays(Integer.parseInt(request.getParameter("days")));
		order.setStatus(status);
		
		orderdao.get().create(order);
		
		session.setAttribute("successOrderSubmit", true);

		response.sendRedirect("order-add");
	}
}
