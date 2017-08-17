package com.scottmarden.beltreviewer.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scottmarden.beltreviewer.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	
	User findByEmail(String email);
	
}
