package by.htp.library.servlets.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.dao.UserDao;

public class AjaxLoginCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		@SuppressWarnings("unchecked")
		final AtomicReference<UserDao> userdao = (AtomicReference<UserDao>) request.getServletContext().getAttribute("userdao");
		String login = request.getParameter("loginname");
		PrintWriter out = response.getWriter();
		
		if(userdao.get().readByLogin(login).getLogin() != null){
			
			out.print(login + " is taken!");
			
		} else {
			
			out.print(login + " is available!");
		}
	}
}
