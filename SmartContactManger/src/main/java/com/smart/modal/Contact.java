package com.smart.modal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cid;

	private String cname;
	
	private String cnickname;
	
	private String work;
	
	private String cemail;
	private String imgurl;
	
    private String phoneString;
  
    private String descrip;
   
	@ManyToOne
	//user cha data serailaze nay hinar
	@JsonIgnore
    private User u;
	public User getU() {
		return u;
	}
	public void setU(User u) {
		this.u = u;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCnickname() {
		return cnickname;
	}
	public void setCnickname(String cnickname) {
		this.cnickname = cnickname;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getCemail() {
		return cemail;
	}
	public void setCemail(String cemail) {
		this.cemail = cemail;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getPhoneString() {
		return phoneString;
	}
	public void setPhoneString(String phoneString) {
		this.phoneString = phoneString;
	}
	public String getDescrip() {
		return descrip;
	}
	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}
	 @Override
		public String toString() {
			return "Contact [cid=" + cid + ", cname=" + cname + ", cnickname=" + cnickname + ", work=" + work + ", cemail="
					+ cemail + ", imgurl=" + imgurl + ", phoneString=" + phoneString + ", descrip=" + descrip + ", u=" + u
					+ "]";
		}
    

}
