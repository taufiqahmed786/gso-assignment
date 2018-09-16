package com.todolist.service;

import java.util.Collection;

import javax.persistence.EntityExistsException;

import com.todolist.model.Item;

public interface ItemService {

    Collection<Item> findAll();

    Item findOne(Integer itemId);
    
    Item findWithComments(Integer itemId);

    Item create(Item item) throws EntityExistsException;
    
    Item markComplete(Integer itemId);
    
    void delete(Integer itemId);
    
    Collection<Item> fetchMostRecentAndPendingItems();
    
    Collection<Item> fetchAllItemsButRecentAndPendingFirst();

}
