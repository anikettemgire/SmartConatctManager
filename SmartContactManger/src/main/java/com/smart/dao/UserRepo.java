package com.smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.smart.modal.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	
	@Query("select u from User u where u.uemail=:uemail")
	public User getUserByName(@Param("uemail")String email);

}
