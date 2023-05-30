package com.smart.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepo;
import com.smart.helper.Meassage;
import com.smart.modal.User;

@Controller
public class HomeControl {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserRepo ur;
	@RequestMapping("/")
	public String Home(Model m) {
		
		m.addAttribute("title", "home-smart Contact Manger");
		return "Home";
	}
	
	@RequestMapping("/about")
	public String about(Model m) {
 
		 m.addAttribute("title", "about-smart Contact Manger");
		
		return "about";
	}
	
	@RequestMapping("/sign")

	public String sign(Model m) {
		m.addAttribute("title", "Sign-smart contact manager");
		m.addAttribute("user", new User());
		
		return "signup";
	}
	
	@PostMapping("/register")
	public String register(@Valid@ModelAttribute("user") User u,BindingResult r,@RequestParam(value="agreement",defaultValue = "false")boolean agreement,Model m,HttpSession se) {
		try {
			//agreme jar empty asel tar userDefined Exceptin Thorie Karnanr msg Geun janar
			if(!agreement) {
				System.out.println("not agree term and condtion");
				throw new Exception("you not agree term and condtion");
			}
			///Error ale Tr chanlanr
			if(r.hasErrors()) {
				
				m.addAttribute("user", u);
				return "signup";
			}
			u.setRole("ROLE_USER");
			u.setEnabled(true);
			u.setImageurl("ani.jpg");
			u.setUpass(passwordEncoder.encode(u.getUpass()));
			//call the query to save the User
	        User result = this.ur.save(u);
	     
			m.addAttribute("user", new User());
			//Apn Ek Msg Class Kela TycHa Consturu 2 Value Patvanar
			se.setAttribute("msg", new Meassage("Succesfull Register!!!","alert-success"));
			return "signup";
		}catch(Exception e) {
			//Exception ale atr tYca Page LA Info Patvanr Manun User Cha Object Patvala Apn
			e.printStackTrace();
			m.addAttribute("user", u);
			//Session vaiable Use Karun Msg Patvla
			//Helper Class Chya Constour La Valuee And Errorr Msg Patvanr
			se.setAttribute("msg", new Meassage("Something went wrong "  + e.getMessage() , "alert-danger"));
			return "signup";
			
		}
	}
	@GetMapping("/log")
	public String login(Model m) {
		m.addAttribute("title", "Login-smart Conact Manage");
		
		return "login";
	}
	

}
