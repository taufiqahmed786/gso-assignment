package com.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todolist.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{
	
	Comment findByCommentId(Integer commentId);

}
