package by.htp.library.bean;

import java.util.Date;

public class Order extends Entity {

	private static final long serialVersionUID = 1L;
	private int bookId;
	private int emplId;
	private int days;
	private Date date;
	private STATUS status;

	public Order() {
		super();
	}

	public Order(int bookId, int emplId, int days, Date date, STATUS status) {
		super();
		this.bookId = bookId;
		this.emplId = emplId;
		this.days = days;
		this.date = date;
		this.status = status;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getEmplId() {
		return emplId;
	}

	public void setEmplId(int emplId) {
		this.emplId = emplId;
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
		result = prime * result + bookId;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + days;
		result = prime * result + emplId;
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
		if (bookId != other.bookId)
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (days != other.days)
			return false;
		if (emplId != other.emplId)
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [bookId=" + bookId + ", emplId=" + emplId + ", days=" + days + ", date=" + date + ", status="
				+ status + ", id=" + id + "]";
	}

}
