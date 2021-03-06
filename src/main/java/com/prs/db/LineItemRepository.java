package com.prs.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.business.LineItem;

public interface LineItemRepository extends JpaRepository<LineItem, Integer> {

	List<LineItem> findAllByRequestId(int id);
	List<LineItem> findAllByRequest(int id);
}
