package by.htp.library.servlets;

import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import by.htp.library.dao.BookDao;
import by.htp.library.dao.EmployeeDao;
import by.htp.library.dao.OrderDao;
import by.htp.library.dao.UserDao;
import by.htp.library.dao.impl.BookDaoDBImpl;
import by.htp.library.dao.impl.EmployeeDaoDBImpl;
import by.htp.library.dao.impl.OrderDaoDBImpl;
import by.htp.library.dao.impl.UserDaoDBImpl;

@WebListener
public class ContextListener implements ServletContextListener {

	private AtomicReference<UserDao> userdao;
	private AtomicReference<BookDao> bookdao;
	private AtomicReference<EmployeeDao> employeedao;
	private AtomicReference<OrderDao> orderdao;

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		userdao = new AtomicReference<>(new UserDaoDBImpl());
		bookdao = new AtomicReference<>(new BookDaoDBImpl());
		employeedao = new AtomicReference<>(new EmployeeDaoDBImpl());
		orderdao = new AtomicReference<>(new OrderDaoDBImpl());

		final ServletContext sc = sce.getServletContext();
		sc.setAttribute("userdao", userdao);
		sc.setAttribute("bookdao", bookdao);
		sc.setAttribute("employeedao", employeedao);
		sc.setAttribute("orderdao", orderdao);

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

		userdao = null;
		bookdao = null;
		employeedao = null;
		orderdao = null;

//		System.out.println("CLOSE CONNECTION");
//		DataSource ds = (DataSource) sce.getServletContext().getAttribute("dbConnection");
//		try {
//			if (!ds.getConnection().isClosed()) {
//				System.out.println("CONNECTION IS CLOSED");
//				ds.getConnection().close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}
}
