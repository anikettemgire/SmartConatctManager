package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.resource.transaction.internal.SynchronizationRegistryStandardImpl;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.ContactRepo;
import com.smart.dao.MyorderRepo;
import com.smart.dao.UserRepo;
import com.smart.helper.Meassage;
import com.smart.modal.Contact;
import com.smart.modal.Myorder;
import com.smart.modal.User;
//import all the libary 
import com.razorpay.*;
@Controller
@RequestMapping("/user")
public class UserController {
	
	  @Autowired
	  private BCryptPasswordEncoder  bCryptPasswordEncoder;
	@Autowired
	private ContactRepo cr;
	@Autowired
	private UserRepo ur;
	@Autowired
	private MyorderRepo mr;

	// for get the username with the hepl of username can send the data to all
	@ModelAttribute
	public void addCommdata(Model m, Principal pr) {
		String username = pr.getName();
		// System.out.println(username);
		User user = this.ur.getUserByName(username);
		m.addAttribute("user", user);
		
		

	}

	@RequestMapping("/index")
	public String dashbord(Model m) {

		m.addAttribute("title", "user-dashbord:-smart-contact-manger");

		return "normal/user_dashbord";
	}

	@GetMapping("/add")
	public String addContact(Model m) {
		m.addAttribute("title", "Add-smart Conatc");
		m.addAttribute("contact", new Contact());
		return "normal/addcontact";
	}

	@PostMapping("/saveContact")
	public String saveContct(@Valid@ModelAttribute Contact co, BindingResult b,
			@RequestParam("imgurl") MultipartFile file, Model m, HttpSession se, Principal pr) {
		// System.out.println(co.getCid());

		try {
//		if(r.hasErrors()) {
//			co.setImgurl("ani.jpg");
//			System.out.println(r);
//			System.out.println(r.hasErrors());
//			m.addAttribute("contact", co);
//		
//			return "normal/addcontact";
//		}

			String name = pr.getName();
			User user = this.ur.getUserByName(name);
			
			
			// processing the File
			if (file.isEmpty()) {
				System.out.println("Em");
				co.setImgurl("contact.jpg");
			} else {
				co.setU(user);
				// set The Image
				co.setImgurl(file.getOriginalFilename());
				// get Path The Resouce
				File f = new ClassPathResource("static/image/").getFile();
				// get Path Pass method
				Path path = Paths.get(f.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("Uplod");

			}

			user.getCon().add(co);
			co.setU(user);
			User res = this.ur.save(user);

			// bY uSING tHE crude repository
			// Contact resuly = this.cr.save(co);
			// System.out.println(resuly);

			se.setAttribute("msg", new Meassage("AddContact Succesful!!!", "alert-success"));

		} catch (Exception e) {

			se.setAttribute("msg", new Meassage("Something wrong" ,"alert-success"));
			return "normal/addcontact";
		}
		return "normal/addcontact";
	}
	//show Conatct
	@GetMapping("/show/{page}")
	public String showContact(@PathVariable("page") Integer page,Model m,Principal pr) {
		m.addAttribute("title","ShowContact-Smart Contact Manger");
		//sending the Cpnoting
		//first Way
		  String name = pr.getName();
		        User user = this.ur.getUserByName(name);
		        //Create An The Objeact Give The Cuurebt page And 3 is For The 3 Rocid I tbale
		           Pageable pa = PageRequest.of(page,5);
		        //By uSing the Contac Resposirty
		           //List Natar Apn Page Object Chnage Kela ahe
		           //pa mage appn object {pass kela
		          Page<Contact> all = this.cr.findByAllContact(user.getUid(),pa);
		           m.addAttribute("Contact", all);
		           //Kontya Pge Var Ahe
		           m.addAttribute("currentpage", page);
		           //totla page Kiti
		           m.addAttribute("totalpage", all.getTotalPages());
		           
                       		       
		return "normal/Show_Contact";
	}
	@GetMapping("{cid}/contact")
	public String partiCular(@PathVariable("cid") int id,Model m,Principal pr) {
		
		
		  Optional<Contact> find = this.cr.findById(id);
		       Contact contact = find.get();
		       //princlle ne user Kadun Ghetla
		       String userName = pr.getName();
		        User user = this.ur.getUserByName(userName);
		        //chcek Karnar Tyach User Cha To The Data
		        if(user.getUid()==contact.getU().getUid()) {
		       
		       m.addAttribute("contact", contact);
		       m.addAttribute("title",contact.getCname() );
		        }
		       
		//m.addAttribute("title", "Contact Detail");
		return "normal/contact_detail";
	}
	@GetMapping("/delete/{id}")
	public String delet(@PathVariable("id") int cid,Model m,Principal pr,HttpSession session) {
		
		//Get The By The  Cid fin Conatc agin ConatcCoptiom .get conatct delet
		 Optional<Contact> contatcoption = this.cr.findById(cid);
		               Contact contact = contatcoption.get();
		               //by check First Get User
                    String name = pr.getName();
                      User user = this.ur.getUserByName(name);
                      if(user.getUid()==contact.getU().getUid()) {
		               this.cr.delete(contact);
		               session.setAttribute("msg", new Meassage("Contact Delete Succesful", "alert-success"));
                      }
		 
		return "redirect:/user/show/0";
	}
	//By Update The Form Get And Set The DAta
	@PostMapping("/updateform/{cid}")
	public String update(@PathVariable("cid") int cid,Model m) {
		
		 Contact contact = this.cr.findById(cid).get();
		 m.addAttribute("contact", contact);
		 m.addAttribute("title", "update:Smart Contact");
		return "normal/update_form";
		
	}
	//updaete Contact Handler
	@RequestMapping(value="/uContact",method = RequestMethod.POST)
	public String update( @ModelAttribute Contact co, BindingResult b,
			@RequestParam("imgurl") MultipartFile file, Model m, HttpSession se, Principal pr) {
		try {
			//fetc Old Contact Deatil
			  Contact oldcontact = this.cr.findById(co.getCid()).get();
			if(!file.isEmpty()) {
				
				//delete first image  form server
				File deletfile= new ClassPathResource("static/image/").getFile();
				File file1=new File(deletfile,oldcontact.getImgurl());
				file1.delete();
			
				//update image new photp
				File f = new ClassPathResource("static/image/").getFile();
				// get Path Pass method
				Path path = Paths.get(f.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				//orignla file navne save karychi file
				co.setImgurl(file.getOriginalFilename());
			}else {
				//jar file empty asel Tar Juni kadychi ani tich Punha Set karyci empty manej jeva apn selet krnar nay theva
				co.setImgurl(oldcontact.getImgurl());
			}
			String name = pr.getName();
                       User user = this.ur.getUserByName(name);
                       co.setU(user);
			this.cr.save(co);
			
			se.setAttribute("msg",new Meassage("Update Contact Succesfull", "alert-success"));
		}catch(Exception e) {
			
		}
		return "redirect:/user/"+co.getCid()+"/contact";
		
	}
	@GetMapping("/profile")
	public String viewProfile(Model m) {
		m.addAttribute("title", "Profile:smart Contact Manger");
		return"normal/user_profile";
	}
	//To VIEE SETTing module
	@GetMapping("/setting")
	public String setting(Model m) {
		
		m.addAttribute("title","Setting");
		
		
		return "normal/setting_form";
	}
	//Change The Password 
	//fetch the data by using the fields the use the requestParam annotaion to fetch the fields
	@PostMapping("/change")
	public String ChnagePasword(@RequestParam("old") String old,@RequestParam("new") String NewPassword,Principal pr,HttpSession se){
	//	System.out.println(old);
		//System.out.println(NewPassword);
		
		   String name = pr.getName();
		   // gte the user by using the user repository
		     User user = this.ur.getUserByName(name);
		                      String upass = user.getUpass();
		            if(this.bCryptPasswordEncoder.matches(old, upass)) {
		            	//passencry karun save karnar
		            	user.setUpass(this.bCryptPasswordEncoder.encode(NewPassword));
		            	this.ur.save(user);
		            	//chanhe the password here if user password(mens old) and GUI password old checcj
		            	se.setAttribute("msg", new Meassage("Password Change","alert-success"));
		            	
		            }else {
		            	
		            	se.setAttribute("msg", new Meassage("Please Enter Correct Old Password","alert-danger"));
		            	//error denar
		            	return "redirect:/user/setting";
		            }
		
		
		return "redirect:/user/index";
	}
	//Payment Handelter 
	@PostMapping("/create_order")
	@ResponseBody
	//konta form nai manun java style Map Function ne kel
	public String Paymet(@RequestBody Map<String , Object> data,Principal pr) throws Exception {
		//System.out.println("order Function");
		// for chekc karn ar data yeto ka eithe 
		System.out.println(data);
		//data kadla map madun Strin gmadhi convert kela ani mag tyla int for madhi ghetla
		         int amt = Integer.parseInt(data.get("amout").toString());
		         //import all the libary 
		         //create Objecrt
		         //and pass the Key And Sercre je apn Razor pay varun kel hote te
		         //Exception Throw kel
		         var client = new RazorpayClient("rzp_test_0YowFv1OI7umEn", "HusHzJXunuPKRSiAm9HY9Kmp");
		         
		         //create Order
		         //create Object Of Json
		         JSONObject ob =new JSONObject();
		         ob.put("amount", amt*100);
		         ob.put("currency", "INR");
		         ob.put("receipt", "txn_235425");
		         
		         //cretating new Order
		         Order order = client.Orders.create(ob);
		         //Print All The Deatil
		      System.out.println(order);
		         //if you want to save it will be save database order 
		   //Save The Data Here;
		         Myorder m=new Myorder();
		         m.setAmout(order.get("amount")+"");
		         m.setOrderId(order.get("id"));
		         m.setPayementId(null);
		         m.setStatus("Created");
		         m.setUser(this.ur.getUserByName(pr.getName()));
		         m.setRecepiet(order.get("receipt"));
		         this.mr.save(m);
		  
		         //order punha Clilent la send keli to String ne 
		        
		return order.toString();
		}
	
	
	@PostMapping("/update_order")
	public  ResponseEntity<?> updateOrder(@RequestBody Map<String,Object>data) {
		
		
	System.out.println(this.mr.findByOrderId(data.get("order_id").toString()));
		
		Myorder myorder = this.mr.findByOrderId(data.get("order_id").toString());
		
		myorder.setPayementId(data.get("payment_id").toString());
		myorder.setStatus(data.get("status").toString());
		this.mr.save(myorder);
		System.out.println("----");
		System.out.print(myorder);
		return ResponseEntity.ok(Map.of("msg",
				"updated"));
		
	}

}
