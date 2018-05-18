package by.htp.library.dao.impl;

import static by.htp.library.dao.util.DBConnectionHelper.connect;
import static by.htp.library.dao.util.DBConnectionHelper.disconnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.htp.library.bean.Book;
import by.htp.library.dao.BookDao;

public class BookDaoDBImpl implements BookDao {

	private static final String SQL_CREATE_BOOK = "INSERT INTO book (title, description, author, year) VALUES (?, ?, ?, ?)";
	private static final String SQL_READ_BOOKS = "SELECT * FROM book";
	private static final String SQL_READ_LAST_BOOKS = "SELECT * FROM book order by book_id desc limit ?";
	private static final String SQL_READ_BOOK_BY_ID = "SELECT * FROM book WHERE book_id = ?";
	private static final String SQL_READ_BOOKS_BY_TITLE = "SELECT * FROM book WHERE title LIKE ?";
	private static final String SQL_UPDATE_BOOK_BY_ID = "UPDATE book SET title = ?, description = ?, author = ?, year = ? WHERE book_id = ?";
	private static final String SQL_DELETE_BOOK_BY_ID = "DELETE FROM book WHERE book_id = ?";

	public void create(Book entity) {

		Connection connection = connect();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_CREATE_BOOK);

			ps.setString(1, entity.getTitle());
			ps.setString(2, entity.getDescription());
			ps.setString(3, entity.getAuthor());
			ps.setInt(4, entity.getYear());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);
	}

	public List<Book> readAll() {

		List<Book> books = new ArrayList<>();
		Connection connection = connect();

		try {

			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(SQL_READ_BOOKS);

			while (rs.next()) {
				Book book = new Book();
				book.setId(rs.getInt("book_id"));
				book.setTitle(rs.getString("title"));
				book.setDescription(rs.getString("description"));
				book.setAuthor(rs.getString("author"));
				book.setYear(rs.getInt("year"));

				books.add(book);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);

		return books;
	}

	public List<Book> readLastBooks(int count) {

		List<Book> books = new ArrayList<>();
		Connection connection = connect();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_READ_LAST_BOOKS);
			ps.setInt(1, count);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Book book = new Book();
				book.setId(rs.getInt("book_id"));
				book.setTitle(rs.getString("title"));
				book.setDescription(rs.getString("description"));
				book.setAuthor(rs.getString("author"));
				book.setYear(rs.getInt("year"));
				books.add(book);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);

		return books;
	}

	public Book read(int id) {

		Connection connection = connect();
		Book book = new Book();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_READ_BOOK_BY_ID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				book.setId(rs.getInt("book_id"));
				book.setTitle(rs.getString("title"));
				book.setDescription(rs.getString("description"));
				book.setAuthor(rs.getString("author"));
				book.setYear(rs.getInt("year"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);

		return book;
	}

	public List<Book> readByTitle(String title) {

		List<Book> books = new ArrayList<>();

		Connection connection = connect();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_READ_BOOKS_BY_TITLE);
			ps.setString(1, "%" + title + "%");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Book book = new Book();
				book.setId(rs.getInt("book_id"));
				book.setTitle(rs.getString("title"));
				book.setDescription(rs.getString("description"));
				book.setAuthor(rs.getString("author"));
				book.setYear(rs.getInt("year"));

				books.add(book);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);

		return books;
	}

	public void update(int id, Book entity) {

		Connection connection = connect();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_BOOK_BY_ID);

			ps.setString(1, entity.getTitle());
			ps.setString(2, entity.getDescription());
			ps.setString(3, entity.getAuthor());
			ps.setInt(4, entity.getYear());
			ps.setInt(5, id);

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);
	}

	public void delete(int id) {

		Connection connection = connect();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_DELETE_BOOK_BY_ID);
			ps.setInt(1, id);

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);
	}
}
