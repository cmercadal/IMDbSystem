package cl.camila.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.camila.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	List<Role> findAll();
	List<Role> findByName(String name);

}
