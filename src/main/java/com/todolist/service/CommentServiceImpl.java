package com.todolist.service;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todolist.model.Comment;
import com.todolist.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	 
	@Autowired
	private CommentRepository commentRepository;
	
	@Override
	public Collection<Comment> findAll() {
		
		Collection<Comment> comments = commentRepository.findAll();
		return comments;
	}

	@Override
	public Comment findOne(Integer commentId) {
		Comment comment = commentRepository.findByCommentId(commentId);
		return comment;
	}

	@Transactional
	@Override
	public Comment create(Comment comment) {
		
		/* Ensure the entity object to be created does NOT exist in the
           repository. Prevent the default behavior of save() which will update
           an existing entity if the entity matching the supplied id exists.
        */
        if (comment.getCommentId() != null) {
            logger.error(
                    "Attempted to create a Comment, but comment attribute was not null.");
            throw new EntityExistsException(
                    "Cannot create new Comment with supplied comment id.  The commentId attribute must be null to create an entity.");
        }

        Comment savedComment = commentRepository.saveAndFlush(comment);
		return savedComment;
	}

	@Transactional
	@Override
	public Comment update(Comment comment) {
		
		/* Ensure the entity object to be updated exists in the repository to
           prevent the default behavior of save() which will persist a new
           entity if the entity matching the id does not exist
        */
		Comment commentToUpdate = findOne(comment.getCommentId());
        if (commentToUpdate == null) {
            logger.error("Attempted to update a Comment, but the entity does not exist.");
            
            throw new NoResultException("Requested Comment not found.");
        }

        commentToUpdate.setComment(comment.getComment());
        commentToUpdate.setCommentDate(comment.getCommentDate());
                
        Comment updatedComment = commentRepository.saveAndFlush(commentToUpdate);
		return updatedComment;
	}

	@Transactional
	@Override
	public void delete(Integer commentId) {
		commentRepository.deleteById(commentId);

	}

}
