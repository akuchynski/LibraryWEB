package by.htp.library.dao;

import java.util.List;

import by.htp.library.bean.Employee;
import by.htp.library.bean.ROLE;
import by.htp.library.bean.User;

public interface UserDao extends BaseDao<User> {

	public void createUserByEmployee(User user, Employee employee);

	public User readByLogin(String title);

	public List<User> readLastUsers(int count);

	public boolean userIsExist(String login, String password);

	public ROLE getRoleByLoginPassword(String login, String password);
	
	public boolean userIsActivated(String login);

}
