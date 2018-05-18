package by.htp.library.servlets.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.bean.Employee;
import by.htp.library.dao.EmployeeDao;
import by.htp.library.dao.ReportDao;

public class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_BOOK_COUNT = 2;

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AtomicReference<EmployeeDao> employeeDao = (AtomicReference<EmployeeDao>) request.getServletContext()
				.getAttribute("employeedao");
		AtomicReference<ReportDao> reportDao = (AtomicReference<ReportDao>) request.getServletContext()
				.getAttribute("reportdao");

		Map<Integer, Integer> emplBooksReport = reportDao.get().getEmployeesBooks(DEFAULT_BOOK_COUNT);
		Map<Integer, Integer> emplBooksDelayReport = reportDao.get().getEmployeesBooksDelay(DEFAULT_BOOK_COUNT);

		List<Employee> employeeList = employeeDao.get().readAll();

		Map<Integer, Employee> employeeMap = employeeList.stream()
				.collect(Collectors.toMap(Employee::getId, item -> item));

		request.getSession().setAttribute("emplBooksReport", emplBooksReport);
		request.getSession().setAttribute("emplBooksDelayReport", emplBooksDelayReport);
		request.getSession().setAttribute("employeeMap", employeeMap);

		String menuPath = (String) request.getSession().getAttribute("menuPath");

		request.getRequestDispatcher(menuPath + "/reports.jsp").forward(request, response);
	}
}
