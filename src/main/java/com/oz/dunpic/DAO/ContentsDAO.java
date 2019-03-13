package com.oz.dunpic.DAO;

import java.util.List;
import java.util.Optional;

import com.oz.dunpic.Entity.Contents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContentsDAO extends JpaRepository<Contents, Integer> {

    //@Query("Select p from Contents p order by id DESC LIMIT 3")
    //SELECT * FROM dunpic.contents order by id desc limit 3;
    //List<Contents> findByRecentContents();

    List<Contents> findTop3ByOrderByIdDesc();

    List<Contents> findTop4ByOrderByIdDesc();


}