package com.oz.dunpic.DAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.oz.dunpic.Entity.Post;

public interface PostDAO extends JpaRepository<Post, Integer>{

	Page<Post> findByCategoryId(int categoryId, Pageable pageable);

	@Query("Select p from Post p where p.push >= 5")
	public Page<Post> Push(Pageable pageable);

}
