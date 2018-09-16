package com.todolist.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentDto {

    private final String comment;
    private final String commentedOn;

    public CommentDto(){
        comment = null;
        commentedOn = null;
    }
    public CommentDto(String comment, String commentedOn){
        this.comment = comment;
        this.commentedOn = commentedOn;
    }

    public String getComment() {
		return comment;
	}
    
    public String getCommentedOn() {
		return commentedOn;
	}
    
    @Override
    public String toString() {
        return "{ \"comment\" :" + comment
                + ", \"commentedOn\" :" + commentedOn + "}";
    }
}
