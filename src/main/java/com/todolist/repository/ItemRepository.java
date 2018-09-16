package com.todolist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.todolist.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>{

	Item findByItemId(Integer itemId);
	
	@Query("select i from Item i where i.done = false order by i.createdOn desc ")
	List<Item> findRecentAndPendingItems();
	
	@Query("select i from Item i order by i.done asc, i.createdOn desc ")
	List<Item> findAllItemsOrderByMostRecentAndPending();
	
	@Query("select i from Item i where i.itemId = ?1 order by i.createdOn desc ")
	Item findWithCommentsByItemId(Integer itemId);
}
