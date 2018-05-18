package by.htp.library.servlets.servlet;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.bean.Book;
import by.htp.library.dao.BookDao;

public class BookAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String menuPath = (String) request.getSession().getAttribute("menuPath");

		request.getRequestDispatcher(menuPath + "/book-add.jsp").forward(request, response);

		request.getSession().setAttribute("successBookSubmit", false);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		AtomicReference<BookDao> bookdao = (AtomicReference<BookDao>) request.getServletContext()
				.getAttribute("bookdao");

		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String author = request.getParameter("author");
		int year = Integer.parseInt(request.getParameter("year"));

		Book book = new Book();

		book.setTitle(title);
		book.setDescription(description);
		book.setAuthor(author);
		book.setYear(year);

		bookdao.get().create(book);

		request.getSession().setAttribute("successBookSubmit", true);

		response.sendRedirect("book-add");
	}
}
