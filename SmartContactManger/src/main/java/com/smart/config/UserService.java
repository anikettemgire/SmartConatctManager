package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.dao.UserRepo;
import com.smart.modal.User;



public class UserService implements UserDetailsService{
    @Autowired
	private UserRepo r;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 
		User user = this.r.getUserByName(username);
		if(user==null) {
			
			throw new UsernameNotFoundException("Coloud Not Found User");
		}
		  UserDeti u=new UserDeti(user);
		return u;
	}

}
