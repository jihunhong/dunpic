package com.oz.dunpic.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oz.dunpic.Entity.UploadFile;

public interface UploadFileDAO extends JpaRepository<UploadFile, Integer>{

	public UploadFile findOneByFileName(String fileName);
	
}
