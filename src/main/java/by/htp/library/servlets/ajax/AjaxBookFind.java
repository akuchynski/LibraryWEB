package by.htp.library.servlets.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.bean.Book;
import by.htp.library.dao.BookDao;

public class AjaxBookFind extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		@SuppressWarnings("unchecked")
		final AtomicReference<BookDao> bookdao = (AtomicReference<BookDao>) request.getServletContext().getAttribute("bookdao");
		String title = request.getParameter("bookSearch");
		PrintWriter out = response.getWriter();
//		List<Book> bookList = bookdao.get().readByTitle(title);
		List<Book> bookList = bookdao.get().readAll();
		if(!bookList.isEmpty()){
			for(Book book: bookList){
				out.println("<option value = " + book.getId() + ">" + book.getTitle() + "</option>");
			}
		}
	}
}
