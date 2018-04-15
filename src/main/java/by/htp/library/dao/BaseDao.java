package by.htp.library.dao;

import java.util.List;

import by.htp.library.bean.Entity;

public interface BaseDao<T extends Entity> {

	public void create(T entity);

	public T read(int id);

	public List<T> readAll();

	public void update(int id, T entity);

	public void delete(int id);

}
