package com.smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.modal.Myorder;

public interface MyorderRepo extends JpaRepository<Myorder, Long> {
	
	public Myorder findByOrderId(String orderId);

}
