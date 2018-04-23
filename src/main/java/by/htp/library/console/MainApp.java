package by.htp.library.console;

import java.util.List;

import by.htp.library.bean.Book;
import by.htp.library.bean.Employee;
import by.htp.library.bean.Order;
import by.htp.library.bean.Report;
import by.htp.library.dao.BookDao;
import by.htp.library.dao.EmployeeDao;
import by.htp.library.dao.OrderDao;
import by.htp.library.dao.impl.BookDaoDBImpl;
import by.htp.library.dao.impl.EmployeeDaoDBImpl;
import by.htp.library.dao.impl.OrderDaoDBImpl;

public class MainApp {

	public static void main(String[] args) {

		BookDao dao1 = new BookDaoDBImpl();
		EmployeeDao dao2 = new EmployeeDaoDBImpl();
		OrderDao dao3 = new OrderDaoDBImpl();

		List<Book> books1 = dao1.readAll();
		List<Book> books2 = dao1.readByTitle("title2");
		
		List<Employee> employees1 = dao2.readAll();
		List<Employee> employees2 = dao2.readBySurname("surname2");
		
		List<Order> orders1 = dao3.readAll();
		
//		Book book5 = new Book("title10", "desc10", "Author10", 1990);
//		dao1.create(book5);
//		
//		Employee employee3 = new Employee ("name3", "surname3", 1991);
//		dao2.create(employee3);
//		
//		dao1.delete(27);
//		dao2.delete(8);
//		
//		dao1.update(28, book5);
//		dao2.update(6, employee3);

		for (Book book : books1) {
			System.out.println(book);
		}

		for (Book book : books2) {
			System.out.println("Found books: " + book);
		}

		for (Employee employee : employees1) {
			System.out.println(employee);
		}

		for (Employee employee : employees2) {
			System.out.println("Found employees: " + employee);
		}
		
		Report.getReportOne();
		Report.getReportTwo();
		
		for (Order order : orders1) {
			System.out.println(order);
		}
		
	}
}
