package by.htp.library.bean;

import static by.htp.library.dao.util.DBConnectionHelper.connect;
import static by.htp.library.dao.util.DBConnectionHelper.disconnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Report {

	private static final String SQL_READ_EMPLOYEES1 = "SELECT (SELECT name FROM employee WHERE emp_id = employee_id) AS name, COUNT(book_id) AS book_count FROM employee_book GROUP BY employee_id HAVING count(book_id) > 2";
	private static final String SQL_READ_EMPLOYEES2 = "SELECT (SELECT name FROM employee WHERE emp_id = employee_id) AS name, COUNT(book_id) AS book_count FROM employee_book GROUP BY employee_id HAVING count(book_id) <= 5";

	public static void getReportOne() {
		Connection connection = connect();
		try {

			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(SQL_READ_EMPLOYEES1);
			System.out.println("The employees who have read more than 2 book:");
			while (rs.next()) {
				System.out.println(rs.getString("name") + ": " + rs.getInt("book_count"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);
	}

	public static void getReportTwo() {

		Connection connection = connect();
		try {

			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(SQL_READ_EMPLOYEES2);
			System.out.println("The employees who have read less than or equal to 5 books:");
			while (rs.next()) {
				System.out.println(rs.getString("name") + ": " + rs.getInt("book_count"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect(connection);
	}
}
