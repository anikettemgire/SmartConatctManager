package com.smart.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.smart.dao.ContactRepo;
import com.smart.dao.UserRepo;
import com.smart.modal.Contact;
import com.smart.modal.User;
//view Ny Respone Body Return Karna 
@RestController
public class SearchController {
	@Autowired
	private ContactRepo cr;
	@Autowired
	private UserRepo ur;
	@GetMapping("/serach/{query}")
	public ResponseEntity<?> serach(@PathVariable("query") String query,Principal p){
		//System.out.println(query);
		           String name = p.getName();
		           User user = this.ur.getUserByName(name);
		   //Method La Call Kela ani Tyat Query And USer Pass KEla 
		 List<Contact> resultcont = this.cr.findByCnameContainingAndU(query,user);
		// System.out.println(resultcont);
		//Return Aplauy Respone Enetit y// JsonIgone Karyche user Serliase ny honar
		return ResponseEntity.ok(resultcont);
	}

}
