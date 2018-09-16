package com.todolist.service;

import java.util.Collection;

import com.todolist.model.Comment;
import com.todolist.model.Item;

public interface CommentService {

    Collection<Comment> findAll();

    Comment findOne(Integer commentId);

    Comment create(Comment comment);
    
    void add(Item item, Comment comment);

    Comment update(Comment comment);
    
    void delete(Integer commentId);
}
