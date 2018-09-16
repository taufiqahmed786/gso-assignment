package com.todolist.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "Item")
@Table(name="item")
public class Item implements Serializable {

	 /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = 12L;

    /**
     * The primary key identifier.
     */
    @Id
    @GeneratedValue
    @Column(name="item_id")
	private Integer itemId;
    
    @Column(name="todo")
	private String todo;
    
    @Column(name="done")
	private Boolean done;
    
    @Column(name="user_id")
	private Integer userId; // created by
	
    @Column(name="created_on")
    private Date createdOn;
	
    @OneToMany(mappedBy= "todoItem")
    private List<Comment> comments;
    
	public Item(){} // default constructor
	
	// perameterized constructor
	public Item(Integer itemId, String todo, Boolean done, Integer userId, Date createdOn,List<Comment> comments) {
		super();
		this.itemId = itemId;
		this.todo = todo;
		this.done = done;
		this.userId = userId;
		this.createdOn = createdOn;
		this.comments = comments;
	}
	
	public Item(String todo, Boolean done, Integer userId, Date createdOn,List<Comment> comments) {
		super();
		this.todo = todo;
		this.done = done;
		this.userId = userId;
		this.createdOn = createdOn;
		this.comments = comments;
	}
	
	public Item(String todo, Boolean done, Integer userId, Date createdOn) {
		super();
		this.todo = todo;
		this.done = done;
		this.userId = userId;
		this.createdOn = createdOn;
		this.comments = new ArrayList<Comment>();
	}
	
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getTodo() {
		return todo;
	}
	public void setTodo(String todo) {
		this.todo = todo;
	}
	public Boolean getDone() {
		return done;
	}
	public void setDone(Boolean done) {
		this.done = done;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public void addComment(Comment comment){
		if(this.comments == null){
			this.comments = new ArrayList<Comment>();
		}
		this.comments.add(comment);
	}
	
		
	@Override
	public String toString() {
		
		return "Item [todo=" + todo + ", done=" + done + ", userId=" + userId + ", createdOn="
				+ createdOn + ", comments=" + comments + "]";
	}
	
}
