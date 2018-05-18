package by.htp.library.servlets.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.dao.EmployeeDao;
import by.htp.library.dao.UserDao;

public class AjaxEmployeeIdCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AtomicReference<UserDao> userdao = (AtomicReference<UserDao>) request.getServletContext()
				.getAttribute("userdao");
		AtomicReference<EmployeeDao> employeedao = (AtomicReference<EmployeeDao>) request.getServletContext()
				.getAttribute("employeedao");
		
		int emplId = Integer.parseInt(request.getParameter("emplId"));
		PrintWriter out = response.getWriter();

		if (userdao.get().read(emplId).getId() != emplId && employeedao.get().read(emplId).getId() == emplId) {
			out.print("true");
		} else {
			out.print("false");
		}
	}
}
