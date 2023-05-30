package com.smart.controller;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepo;
import com.smart.helper.Meassage;
import com.smart.modal.User;
import com.smart.service.EmailService;

@Controller
public class ForgoutController {
	//for inject the Object Of the Emial Service Here
	@Autowired
	private EmailService es;
	//For Chekc The Mail Id PRES USER REPo;
	@Autowired
	private UserRepo ur;
	//For the Brcrypted Pass
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/forgot")
	public String forgoutLink() {
		
		
		return "forgot_form";
	}
	@PostMapping("/getmail")
    public String GetMail(@RequestParam("mail") String mail,Model m,HttpSession session) {
		Random r=new Random();
		 int otp = r.nextInt(9999);
         String to=mail;
         String sub="OTP";
         String mess= + otp + "  is your OTP.Please do not share it with anybody";
       // System.out.println(otp);
        
       
         
         boolean f=this.es.sendMail(to, sub, mess);
         if(f) {
        	// System.out.println("send mail");
        	 session.setAttribute("sendotp", otp);
        	  session.setAttribute("mail", mail);
        	 return "verify_otp";
         }
         else {
        	// System.out.println("not send");
        	 session.setAttribute("msg", new Meassage("Chech Your Mail agin", "alert-danger"));
        	 return "forgot_form";
         }
		
		
		
	}
	@PostMapping("/verifotp")
		public String checkOtp(@RequestParam("otp") int otp,HttpSession session) {
			
			
			//get The Send OTP AND MAIL
			int sendOTP = (int) session.getAttribute("sendotp");
		  String email = (String) session.getAttribute("mail");
		  //TO CHEKC WITH BOT OTP
		  
		  if(sendOTP==otp) {
			  
			  User user = this.ur.getUserByName(email);
			  if(user==null) {
				  //send Error msg
				  session.setAttribute("msg", new Meassage("User Does Not Exit...", "alert-danger"));
				  return "forgot_form";
			  }else {
				  
				  
				  //send the change password form
				  
			  }
			  
			  return "change_Password";
		  }else {
			  
			  
			  session.setAttribute("msg",  new Meassage("Your Are Enter Wrong Otp", "alert-danger"));
			  return "verify_otp";
		  }	
			//return "change_Password";
			
		}
	   @PostMapping("/changepass")
	   public String ChangePasswordd(@RequestParam("newpass") String  password,Model m,HttpSession session) {
		   
		                //System.out.println("new Psss"+password);
		                String emil = (String) session.getAttribute("mail");
		                              User user = this.ur.getUserByName(emil);
		                              user.setUpass(this.bCryptPasswordEncoder.encode(password));
		                                this.ur.save(user);
		        // session.setAttribute("message", "Chnage Password Succesful");
		   
		   return "redirect:/log?change=password chnage succesful ";
	   }
		
		
	

}
