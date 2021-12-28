package cl.camila.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.camila.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	//vienen con jpaReppository:
	//findAll
	//findById
	//save
	//update
	//delete
	
	List<User> findAll();
	User findByUsername(String username);
	User findUserByEmail(String email);

}
