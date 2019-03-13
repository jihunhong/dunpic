package com.oz.dunpic.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oz.dunpic.Entity.Notice;

public interface NoticeDAO extends JpaRepository<Notice, Integer>{

}
