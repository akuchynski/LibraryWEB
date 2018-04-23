package by.htp.library.dao;

import by.htp.library.bean.Employee;
import by.htp.library.bean.ROLE;
import by.htp.library.bean.User;

public interface UserDao extends BaseDao<User> {
	
	public void createUserByEmployee(User user, Employee employee);
	public User readByLogin(String title);
	public boolean userIsExist(String login, String password);
	public ROLE getRoleByLoginPassword(String login, String password);

}
