package com.todolist.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.todolist.model.Comment;
import com.todolist.model.Item;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDto {
	
	public static final String COMPLETED = "Completed";
	public static final String PENDING = "Pending";
	
    private final String task;
    private final String status;
    private final List<CommentDto> comments;

    public ItemDto(){
        task = null;
        status = null;
        comments = null;
    }
    public ItemDto(String task, String status, List<CommentDto> comments ){
        this.task = task;
        this.status = status;
        this.comments = comments;
    }

    public String getTask() {
		return task;
	}
    
    public String getStatus() {
		return status;
	}
    
    public List<CommentDto> getComments() {
		return comments;
	}
    
    @Override
    public String toString() {
    	StringBuffer commentJson = new StringBuffer();
    	commentJson.append("[");
    	for(CommentDto comment : comments){
    		commentJson.append(comment.toString()).append(", ");
    	}
    	commentJson.append("]");
        return "{ \"task\" :" + task
                + ", \"status\" :" + status 
                + ", \"comments\" :" + commentJson.toString() + "}";
    }
    
    public static ItemDto create(Item item){
    	String status = item.getDone() ? COMPLETED : PENDING;
    	List<CommentDto> commentsList = new ArrayList<CommentDto>();
    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	for(Comment comment : item.getComments()){
    		commentsList.add(new CommentDto(comment.getComment(),dateFormat.format(comment.getCommentDate())));
    	}
    	return new ItemDto(item.getTodo(),status,commentsList);
    }
}
