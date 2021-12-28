package cl.camila.service;

import java.util.List;

import cl.camila.model.User;

public interface UserService {
	
	public List<User> findAll();
	User findByUsername(String username);
	User findUserById(Long id);
	User findUserByEmail(String email);
	public void add(User user);
	void saveWithUserRole(User user);
	void saveUserWithAdminRole(User user);
	


}
