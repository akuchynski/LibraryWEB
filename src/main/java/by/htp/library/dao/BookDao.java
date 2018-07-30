package by.htp.library.dao;

import java.util.List;

import by.htp.library.bean.Book;

public interface BookDao extends BaseDao<Book> {
	
	public List<Book> readAvailableBooks();
	
	public List<Book> readLastBooks(int quantity);
	
	public List<Book> readByTitle(String title);
	
	public void incrementBookQuantity(int id);
	
	public void decrementBookQuantity(int id);

}
