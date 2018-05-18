package by.htp.library.dao;

import static by.htp.library.dao.util.DBConnectionHelper.connect;
import static by.htp.library.dao.util.DBConnectionHelper.disconnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ReportDao {

	private static final String SQL_READ_EMPLOYEES_BOOKS = "SELECT employee_id, COUNT(book_id) AS book_count FROM employee_book GROUP BY employee_id HAVING count(book_id) >= ?";
	private static final String SQL_READ_EMPLOYEES_BOOKS_DELAY = "SELECT employee_id, COUNT(book_id) AS book_count FROM employee_book WHERE status = 'DELIVERED' AND (DATEDIFF(CURDATE(), date) > days) GROUP BY employee_id HAVING count(book_id) >= ?";

	public Map<Integer, Integer> getEmployeesBooks(int count) {

		Map<Integer, Integer> report = new HashMap<>();

		Connection connection = connect();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_READ_EMPLOYEES_BOOKS);
			ps.setInt(1, count);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				report.put(rs.getInt("employee_id"), rs.getInt("book_count"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);

		return report;
	}

	public Map<Integer, Integer> getEmployeesBooksDelay(int count) {

		Map<Integer, Integer> report = new HashMap<>();

		Connection connection = connect();

		try {

			PreparedStatement ps = connection.prepareStatement(SQL_READ_EMPLOYEES_BOOKS_DELAY);
			ps.setInt(1, count);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				report.put(rs.getInt("employee_id"), rs.getInt("book_count"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);

		return report;
	}
}
