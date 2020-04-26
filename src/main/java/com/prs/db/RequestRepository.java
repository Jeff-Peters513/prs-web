package com.prs.db;

import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.business.Request;



public interface RequestRepository extends JpaRepository<Request, Integer> {

	//List<Request> findById(int id);
	
	@ManyToOne
	@JoinColumn(name = "userId")
	List<Request> findByUserIdNot(Integer userId);

}
