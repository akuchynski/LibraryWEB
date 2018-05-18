package by.htp.library.servlets.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import by.htp.library.bean.STATUS;
import by.htp.library.dao.BookDao;
import by.htp.library.dao.EmployeeDao;
import by.htp.library.dao.OrderDao;

public class OrderEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AtomicReference<BookDao> bookdao = (AtomicReference<BookDao>) request.getServletContext()
				.getAttribute("bookdao");
		AtomicReference<EmployeeDao> employeedao = (AtomicReference<EmployeeDao>) request.getServletContext()
				.getAttribute("employeedao");
		AtomicReference<OrderDao> orderdao = (AtomicReference<OrderDao>) request.getServletContext()
				.getAttribute("orderdao");

		List<Book> bookList = bookdao.get().readAll();
		request.getSession().setAttribute("bookList", bookList);

		List<Employee> employeeList = employeedao.get().readAll();
		request.getSession().setAttribute("employeeList", employeeList);
		int editId = 0;
		if (request.getParameter("num") != null) {
			editId = Integer.parseInt(request.getParameter("num"));
		}

		request.getSession().setAttribute("editId", editId);

		Order editOrder = orderdao.get().read(editId);
		request.getSession().setAttribute("editOrder", editOrder);

		Book editBook = bookdao.get().read(editOrder.getBookId());
		request.getSession().setAttribute("editBook", editBook);
		Employee editEmployee = employeedao.get().read(editOrder.getEmplId());
		request.getSession().setAttribute("editEmployee", editEmployee);

		String menuPath = (String) request.getSession().getAttribute("menuPath");

		request.getRequestDispatcher(menuPath + "/order-edit.jsp").forward(request, response);

		request.getSession().removeAttribute("messageClass");
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AtomicReference<OrderDao> orderdao = (AtomicReference<OrderDao>) request.getServletContext()
				.getAttribute("orderdao");
		HttpSession session = request.getSession();
		int id = (Integer) session.getAttribute("editId");
		String submitType = request.getParameter("submit");

		if (submitType.equals("delete")) {
			orderdao.get().delete(id);
			session.setAttribute("messageClass", "order-delete-success");
			response.sendRedirect("order-list");
		} else if (submitType.equals("update")) {
			Order order = new Order();
			order.setEmplId(Integer.parseInt(request.getParameter("emplId")));
			order.setBookId(Integer.parseInt(request.getParameter("bookId")));
			order.setDays(Integer.parseInt(request.getParameter("days")));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				order.setDate(format.parse(request.getParameter("date")));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			order.setStatus(STATUS.valueOf(request.getParameter("status")));
			orderdao.get().update(id, order);
			session.setAttribute("messageClass", "order-update-success");
			response.sendRedirect("order-list");
		} else {
			response.sendRedirect("order-edit");
		}
	}
}
