package com.smart.modal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Myorder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long myOrderId;
	
	private String orderId;
	private String amout;
	private String recepiet;
	private String status;
	private String payementId;
	
	@ManyToOne
	private User user;

	public Long getMyOrderId() {
		return myOrderId;
	}

	public void setMyOrderId(Long myOrderId) {
		this.myOrderId = myOrderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAmout() {
		return amout;
	}

	public void setAmout(String amout) {
		this.amout = amout;
	}

	public String getRecepiet() {
		return recepiet;
	}

	public void setRecepiet(String recepiet) {
		this.recepiet = recepiet;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPayementId() {
		return payementId;
	}

	public void setPayementId(String payementId) {
		this.payementId = payementId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Myorder [myOrderId=" + myOrderId + ", orderId=" + orderId + ", amout=" + amout + ", recepiet="
				+ recepiet + ", status=" + status + ", payementId=" + payementId + ", user=" + user + "]";
	}
	

}