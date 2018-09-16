package com.todolist.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity(name = "Comment")
@Table(name = "comment")
public class Comment implements Serializable {

	 /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The primary key identifier.
     */
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
	private Integer commentId;
    
    @Column(name = "comment")
	private String comment;
    
    @Column(name = "comment_date")
	private Date commentDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_item")
    private Item todoItem;
	
	public Comment(){} // default constructor
	
	// perameterized constructor
	public Comment(Integer commnetId, String comment, Date commentDate) {
		super();
		this.commentId = commnetId;
		this.comment = comment;
		this.commentDate = commentDate;
	}
	
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}	
	public Item getTodoItem() {
		return todoItem;
	}
	public void setTodoItem(Item todoItem) {
		this.todoItem = todoItem;
	}
	
	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", comment=" + comment + ", commentDate=" + commentDate + "]";
	}
	
}
