package by.htp.library.bean;

import java.io.Serializable;

public abstract class Entity implements Serializable {

	private static final long serialVersionUID = 1L;
	protected int id;

	protected Entity() {
		super();
	}

	protected Entity(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
