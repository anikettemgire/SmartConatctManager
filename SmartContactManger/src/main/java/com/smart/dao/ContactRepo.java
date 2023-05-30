package com.smart.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.modal.Contact;
import com.smart.modal.User;

public interface ContactRepo extends JpaRepository<Contact, Integer>{
	@Query("from Contact as c where c.u.id=:uid")
	//pageble can containe two Type Info
	//1)Current Page
	//2)Info CurretPage
	public Page<Contact> findByAllContact(@Param("uid") int id,Pageable pageable);
	
	//Seach Function stahi apn method use karnar
	//Containeg Staho Tya Navne Containe Karto Tyastahi
	//keword/name
	//asa user tyche nav Contaoinr kel ahe Mnun Apm Method Keli
	
	public List<Contact>findByCnameContainingAndU(String query,User u);

	 
}
