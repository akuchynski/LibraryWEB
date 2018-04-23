package by.htp.library.bean;

import java.util.Date;

public class Order extends Entity{
	private Book book;
	private Employee employee;
	private int days;
	private Date date;
	private STATUS status;
	
	public Order() {
		super();
	}

	public Order(Book book, Employee employee, int days, Date date, STATUS status) {
		super();
		this.book = book;
		this.employee = employee;
		this.days = days;
		this.date = date;
		this.status = status;
	}
	
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + days;
		result = prime * result + ((employee == null) ? 0 : employee.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (book == null) {
			if (other.book != null)
				return false;
		} else if (!book.equals(other.book))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (days != other.days)
			return false;
		if (employee == null) {
			if (other.employee != null)
				return false;
		} else if (!employee.equals(other.employee))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [book=" + book + ", employee=" + employee + ", days=" + days + ", date=" + date + ", status="
				+ status + "]";
	}
}
