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


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String menuPath = (String)request.getSession().getAttribute("menuPath");
		
		request.getRequestDispatcher(menuPath + "/book-add.jsp").forward(request, response);
		
		request.getSession().setAttribute("successBookSubmit", false);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		final AtomicReference<BookDao> bookdao = (AtomicReference<BookDao>) request.getServletContext().getAttribute("bookdao");
		
		final String title = request.getParameter("title");
		final String description = request.getParameter("description");
		final String author = request.getParameter("author");
		final int year = Integer.parseInt(request.getParameter("year"));
		
		final Book book = new Book();
		
		book.setTitle(title);
		book.setDescription(description);
		book.setAuthor(author);
		book.setYear(year);
		
		bookdao.get().create(book);
		
		request.getSession().setAttribute("successBookSubmit", true);

		response.sendRedirect("book-add");
	}
}
