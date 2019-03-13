package com.oz.dunpic.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oz.dunpic.Entity.Category;

public interface CategoryDAO extends JpaRepository<Category, Integer> {

}
