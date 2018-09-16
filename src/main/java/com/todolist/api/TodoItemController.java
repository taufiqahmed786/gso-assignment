package com.todolist.api;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.model.Comment;
import com.todolist.model.Item;
import com.todolist.service.CommentService;
import com.todolist.service.ItemService;

/**
 * The TodoItemController class is a RESTful web service controller.
 */
@RestController
public class TodoItemController extends BaseController {

    /**
     * The ItemService business service.
     */
    @Autowired
    private ItemService itemService;

    /**
     * The CommentService business service.
     */
    @Autowired
    private CommentService commentService;

    /**
     * Web service endpoint to fetch all Todo items.
     * Most recent and pending first sorted. 
     * 
     * @return A ResponseEntity containing a List of all Todo item objects.
     * @throws Exception Thrown if a problem occurs completing the request.
     */
    @GetMapping("/todo/items/all")
    public ResponseEntity<Collection<Item>> getAllItems() {
        logger.info("# getAllItems - start");
        
        Collection<Item> todoItems = itemService.fetchAllItemsButRecentAndPendingFirst();

        logger.info("# getAllItems - end");
        return new ResponseEntity<Collection<Item>>(todoItems,HttpStatus.OK);
    }
    
    /**
     * Web service endpoint to fetch recent and pending Todo items only.
     * 
     * @return A ResponseEntity containing a List of recent and pending Todo item objects.
     * @throws Exception Thrown if a problem occurs completing the request.
     */
    @GetMapping("/todo/items")
    public ResponseEntity<Collection<Item>> getRecentPendingItems() {
        logger.info("# getRecentPendingItems - start");

        Collection<Item> recentPendingTodoItems = itemService.fetchMostRecentAndPendingItems();

        logger.info("# getRecentPendingItems - end");
        return new ResponseEntity<Collection<Item>>(recentPendingTodoItems,HttpStatus.OK);
    }
    
    /**
     * Web service endpoint to fetch a Todo item using item id.
     * 
     * @return A ResponseEntity containing a Todo item object.
     * @throws Exception Thrown if a problem occurs completing the request.
     */
    @GetMapping("/todo/items/{itemId}")
    public ResponseEntity<Item> getTodoItemByItemID(@PathVariable Integer itemId) {
        logger.info("# getTodoItemByItemID - start");

        Item todoItem = itemService.findOne(itemId);
        if (todoItem == null) {
        	logger.info("# getTodoItemByItemID - not found");
            return new ResponseEntity<Item>(HttpStatus.NOT_FOUND);
        }

        logger.info("# getTodoItemByItemID - end");
        return new ResponseEntity<Item>(todoItem,HttpStatus.OK);
    }


    /**
     * Web service endpoint to create a single Todo entity. The HTTP request
     * body is expected to contain a Todo Item object in JSON format. The
     * Todo is persisted in the data repository.
     * 
     * If created successfully, the service returns an empty response body as 
     * JSON with HTTP status 201.
     * 
     * If not created successfully, the service returns an empty response body
     * with HTTP status 500.
     * 
     * @param item The Item object to be created.
     * @return A ResponseEntity with an empty response body and a HTTP status
     *         code as described in the method comment.
     * @throws Exception Thrown if a problem occurs completing the request.
     */
    @PostMapping("/todo/items")
    public ResponseEntity<Item> createTodoItem(@RequestBody Item todoItem) 
    		throws Exception{
    	logger.info("# getTodoItemByItemID - start");

        itemService.create(todoItem);

        logger.info("# getTodoItemByItemID - end");
        return new ResponseEntity<Item>(HttpStatus.CREATED);
    }
    
    /**
     * Web service endpoint to delete a single Item entity. The HTTP request
     * body is empty. The primary key identifier of the todo item to be deleted
     * is supplied in the URL as a path variable.
     * 
     * If deleted successfully, the service returns an empty response body with
     * HTTP status 204.
     * 
     * If not deleted successfully, the service returns an empty response body
     * with HTTP status 500.
     * 
     * @param itemId An Integer URL path variable containing the Todo Item primary key
     *        identifier.
     * @return A ResponseEntity with an empty response body and a HTTP status
     *         code as described in the method comment.
     * @throws Exception Throw if a problem occurs completing the request.
     */
    @DeleteMapping("/todo/items/{itemId}")
    public ResponseEntity<Item> deleteTodoItem(@PathVariable Integer itemId)
            throws Exception {
    	logger.info("# deleteTodoItem - start");

        itemService.delete(itemId);

        logger.info("# deleteTodoItem - end");
        return new ResponseEntity<Item>(HttpStatus.NO_CONTENT);
    }

    /**
     * Web service endpoint to mark Todo Item as done. The HTTP request
     * body is empty. The primary key identifier of the todo item to be 
     * updated is supplied in the URL as a path variable.
     * 
     * If updated successfully, the service returns an empty response body as
     * JSON with HTTP status 200.
     * 
     * If not found, the service returns an empty response body and HTTP status
     * 404.
     * 
     * If not updated successfully, the service returns an empty response body
     * with HTTP status 500.
     * 
     * @param itemId The itemId of TodoItem object to be marked as done.
     * @return A ResponseEntity with an empty response body and a HTTP status
     *         code as described in the method comment.
     * @throws Exception Thrown if a problem occurs completing the request.
     */
    @PutMapping("/todo/items/{itemId}")
    public ResponseEntity<Item> markTodoItemComplete(@PathVariable Integer itemId) {
    	logger.info("# markTodoItemComplete - start");

        itemService.markComplete(itemId);

        logger.info("# markTodoItemComplete - end");
        return new ResponseEntity<Item>(HttpStatus.NO_CONTENT);
    }
    
    /**
     * Web service endpoint to add Comment to Todo entity. The HTTP request
     * body is expected to contain a Comment object in JSON format. The primary key 
     * identifier of the commented todo item is supplied in the URL as a path variable.
     * The Comment is persisted in the data repository.
     * 
     * If added successfully, the persisted List of Todo Item is returned as JSON with
     * HTTP status 201.
     * 
     * If not created successfully, the service returns an empty response body
     * with HTTP status 500.
     * 
     * @param comment The Comment object to be added.
     * @param itemId The itemId of commented Todo Item 
     * @return A ResponseEntity containing a list of Comment object, if created
     *         successfully, and a HTTP status code as described in the method
     *         comment.
     * @throws Exception Thrown if a problem occurs completing the request.
     */
    @PostMapping("/todo/items/{itemId}/comments")
    public ResponseEntity<Collection<Item>> addComment(@PathVariable Integer itemId, @RequestBody Comment comment) 
    		throws Exception{
    	logger.info("# addComment - start");

    	Item todoItem = itemService.findOne(itemId);
        if (todoItem == null) {
        	logger.info("# addComment - item not found");
            return new ResponseEntity<Collection<Item>>(HttpStatus.NOT_FOUND);
        }
        
        // add comment
        Comment newComment = new Comment(null, "Test Comment", new Date(System.currentTimeMillis()));
        //comment.setCommentDate(new Date(System.currentTimeMillis())); // set current date/time
        commentService.add(todoItem,comment);
        
        // fetch comment list
        Item updatedItem = itemService.findOne(itemId);
        List<Item> updatedItemList = new ArrayList<Item>();
        updatedItemList.add(updatedItem);

        logger.info("# addComment - end");
        return new ResponseEntity<Collection<Item>>(updatedItemList,HttpStatus.OK);
    }

}
