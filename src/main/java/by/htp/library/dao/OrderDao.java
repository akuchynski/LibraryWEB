package by.htp.library.dao;

import java.util.List;

import by.htp.library.bean.Order;

public interface OrderDao extends BaseDao<Order> {
	
	public List<Order> readOrdersByStatus(String status);

}