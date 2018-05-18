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
import by.htp.library.dao.BookDao;

public class BookEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AtomicReference<BookDao> bookdao = (AtomicReference<BookDao>) request.getServletContext()
				.getAttribute("bookdao");

		List<Book> bookList = bookdao.get().readAll();
		request.getSession().setAttribute("bookList", bookList);

		int editId = 0;
		if (request.getParameter("num") != null) {
			editId = Integer.parseInt(request.getParameter("num"));
		}

		request.getSession().setAttribute("editId", editId);

		Book editBook = bookdao.get().read(editId);
		request.getSession().setAttribute("editBook", editBook);

		String menuPath = (String) request.getSession().getAttribute("menuPath");

		request.getRequestDispatcher(menuPath + "/book-edit.jsp").forward(request, response);

		request.getSession().removeAttribute("message");
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AtomicReference<BookDao> bookdao = (AtomicReference<BookDao>) request.getServletContext()
				.getAttribute("bookdao");
		
		HttpSession session = request.getSession();
		
		int id = (Integer) session.getAttribute("editId");
		String submitType = request.getParameter("submit");

		if (submitType.equals("delete")) {
			bookdao.get().delete(id);
			session.setAttribute("messageClass", "book-delete-success");
			response.sendRedirect("book-list");
		} else if (submitType.equals("update")) {
			Book book = new Book();
			book.setTitle(request.getParameter("title"));
			book.setDescription(request.getParameter("description"));
			book.setAuthor(request.getParameter("author"));
			book.setYear(Integer.parseInt(request.getParameter("year")));
			bookdao.get().update(id, book);
			session.setAttribute("messageClass", "book-update-success");
			response.sendRedirect("book-list");
		} else {
			response.sendRedirect("book-edit");
		}
	}
}
