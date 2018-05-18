package by.htp.library.servlets.servlet;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.library.bean.Employee;
import by.htp.library.bean.User;
import by.htp.library.dao.EmployeeDao;
import by.htp.library.dao.UserDao;

public class UserEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String SAVE_DIRECTORY = "upload";

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AtomicReference<EmployeeDao> employeedao = (AtomicReference<EmployeeDao>) request.getServletContext()
				.getAttribute("employeedao");
		AtomicReference<UserDao> userdao = (AtomicReference<UserDao>) request.getServletContext()
				.getAttribute("userdao");

		HttpSession session = request.getSession();

		int editId = (Integer) session.getAttribute("currentId");
		if (request.getParameter("num") != null) {
			editId = Integer.parseInt(request.getParameter("num"));
		}

		session.setAttribute("editId", editId);

		User editUser = userdao.get().read(editId);
		session.setAttribute("editUser", editUser);

		Employee editEmployee = employeedao.get().read(editId);
		session.setAttribute("editEmployee", editEmployee);

		String menuPath = (String) session.getAttribute("menuPath");
		request.getRequestDispatcher(menuPath + "/user-edit.jsp").forward(request, response);

		session.removeAttribute("messageClass");
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AtomicReference<EmployeeDao> employeedao = (AtomicReference<EmployeeDao>) request.getServletContext()
				.getAttribute("employeedao");
		AtomicReference<UserDao> userdao = (AtomicReference<UserDao>) request.getServletContext()
				.getAttribute("userdao");

		HttpSession session = request.getSession();
		User user = new User();
		Employee employee = new Employee();
		int id = (Integer) session.getAttribute("editId");
		String submitType = request.getParameter("submit");

		if (submitType.equals("delete")) {
			userdao.get().delete(id);
			session.setAttribute("messageClass", "user-delete-success");
			response.sendRedirect("user-edit");
		} else if (submitType.equals("update")) {
			user.setEmail(request.getParameter("email"));
			if (request.getParameter("new_password").isEmpty()) {
				user.setPassword(request.getParameter("old_password"));
			} else {
				user.setPassword(request.getParameter("new_password"));
			}
			employee.setName(request.getParameter("name"));
			employee.setSurname(request.getParameter("surname"));
			employee.setYear(Integer.parseInt(request.getParameter("year")));

			employeedao.get().update(id, employee);
			userdao.get().update(id, user);

			Employee currentEmployee = employeedao.get().read(id);
			User currentUser = userdao.get().read(id);
			request.getSession().setAttribute("currentEmployee", currentEmployee);
			request.getSession().setAttribute("currentUser", currentUser);

			session.setAttribute("messageClass", "user-update-success");
			response.sendRedirect("user-edit");
		} else {
			session.setAttribute("messageClass", "user-pass-error");
			response.sendRedirect("user-edit");
		}
	}
}
