package com.todolist.service;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todolist.model.Item;
import com.todolist.repository.CommentRepository;
import com.todolist.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	 
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Override
	public Collection<Item> findAll() {
		
		Collection<Item> items = itemRepository.findAll();
		return items;
	}

	@Override
	public Item findOne(Integer itemId) {
		Item item = itemRepository.findByItemId(itemId);
		return item;
	}
	
	@Override
	public Item findWithComments(Integer itemId) {
		Item item = itemRepository.findWithCommentsByItemId(itemId);
		item.setComments(commentRepository.findByItemId(itemId));
		return item;
	}

	@Transactional
	@Override
	public Item create(Item item) throws EntityExistsException{
		
		/* Ensure the entity object to be created does NOT exist in the
           repository. Prevent the default behavior of save() which will update
           an existing entity if the entity matching the supplied id exists.
        */
        if (item.getItemId() != null) {
            logger.error(
                    "Attempted to create an Item, but itemId attribute was not null.");
            throw new EntityExistsException(
                    "Cannot create new Item with supplied item id.  The itemId attribute must be null to create an entity.");
        }

        Item savedItem = itemRepository.saveAndFlush(item);
		return savedItem;
	}
	
	@Transactional
	@Override
	public Item markComplete(Integer itemId){
		
		/* Ensure the entity object to be updated exists in the repository to
           prevent the default behavior of save() which will persist a new
           entity if the entity matching the id does not exist
        */
        Item itemToUpdate = findOne(itemId);
        if (itemToUpdate == null) {
            logger.error("Attempted to mark complete an Item, but the entity does not exist.");
            
            throw new NoResultException("Requested Item not found.");
        }

        itemToUpdate.setDone(true); // marking as complete
                
        Item updatedItem = itemRepository.saveAndFlush(itemToUpdate);
		return updatedItem;
	}

	@Transactional
	@Override
	public void delete(Integer itemId) {
		itemRepository.deleteById(itemId);

	}
	
	
	@Override
	public Collection<Item> fetchMostRecentAndPendingItems(){
		
		Collection<Item> mostRecentPendingItems = itemRepository.findRecentAndPendingItems();
		return mostRecentPendingItems;
	}
	
	@Override
	public Collection<Item> fetchAllItemsButRecentAndPendingFirst(){
		Collection<Item> allItemsInOrder = itemRepository.findAllItemsOrderByMostRecentAndPending();
		return allItemsInOrder;
	}

}
