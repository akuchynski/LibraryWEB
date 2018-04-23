package by.htp.library.dao;

import by.htp.library.bean.Book;
import by.htp.library.bean.Employee;
import by.htp.library.bean.Order;

public interface OrderDao extends BaseDao<Order> {
	
	public void createOrderByBookAndEmployee(Book book, Employee employee);

}
