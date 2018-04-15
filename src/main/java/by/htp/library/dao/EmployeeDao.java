package by.htp.library.dao;

import java.util.List;

import by.htp.library.bean.Employee;

public interface EmployeeDao extends BaseDao<Employee> {

	public List<Employee> readBySurname(String title);

}
