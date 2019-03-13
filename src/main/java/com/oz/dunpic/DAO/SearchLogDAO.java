package com.oz.dunpic.DAO;

import com.oz.dunpic.Entity.SearchLog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchLogDAO extends JpaRepository<SearchLog, Integer>{
    
}