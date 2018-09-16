package com.todolist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.todolist.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{
	
	Comment findByCommentId(Integer commentId);
	
	@Query(value = "SELECT * FROM COMMENT WHERE FK_ITEM = ?1 ORDER BY COMMENT_DATE DESC", nativeQuery = true)
	List<Comment> findByItemId(Integer itemId);
}
