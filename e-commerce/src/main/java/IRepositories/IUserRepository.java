package IRepositories;

import java.util.List;

import Entity.User;

public interface IUserRepository {
	
	
	boolean authentication(User user );
	User loggedInUser();
	User getUserById(int id);
	List<User> getAllUsers();
	void addUser(User user);
	void updateUser(User user,User new_user);
	void deleteUser(User user);
	
	User getAdmin();
}
