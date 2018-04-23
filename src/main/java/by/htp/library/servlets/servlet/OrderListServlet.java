package by.htp.library.servlets.servlet;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.bean.Order;
import by.htp.library.dao.OrderDao;

public class OrderListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		@SuppressWarnings("unchecked")
		final AtomicReference<OrderDao> orderdao = (AtomicReference<OrderDao>) request.getServletContext().getAttribute("orderdao");
		
		final List<Order> orderList = orderdao.get().readAll();
		request.getSession().setAttribute("orderList", orderList);
		String menuPath = (String)request.getSession().getAttribute("menuPath");
		
		request.getRequestDispatcher(menuPath + "/order-list.jsp").forward(request, response);
	}
}
