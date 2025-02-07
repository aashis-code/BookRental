package com.bookrental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookrental.model.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
	
	Optional<Role> findByName(String name);
	
	List<Role> findAllByDeleted(Pageable page, Boolean deleted);
	
	@Modifying
	@Query(value = "update role set deleted = true where id =?1", nativeQuery = true)
	int deleteRoleById(Integer roleId);

}
