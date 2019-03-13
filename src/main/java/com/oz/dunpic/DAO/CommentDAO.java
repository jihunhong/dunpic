package com.oz.dunpic.DAO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oz.dunpic.Entity.Comment;
import com.oz.dunpic.Entity.Post;

public interface CommentDAO extends JpaRepository<Comment, Integer>{

	@Query("Select p from Comment p where post_id = :post_id")
	List<Comment> findByPost_id(@Param("post_id") int post_id);
	
}
