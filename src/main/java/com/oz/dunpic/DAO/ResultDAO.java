package com.oz.dunpic.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oz.dunpic.Entity.Result;

public interface ResultDAO extends JpaRepository<Result, Integer>{
	
	@Query(value="Select * from card a LEFT OUTER JOIN effect on a.id=effect.card_id where effect.groupname=:groupname and effect.card_id in (SELECT a.id FROM card a Inner JOIN effect b ON a.id = b.card_id where part like %:part% and effect=:effect) order by effect.value desc", nativeQuery=true)
	List<Result> findBySearch(@Param("part") String part, @Param("effect") String effect, @Param("groupname") String groupname);

	@Query(value="SELECT * FROM card a Inner JOIN effect b ON a.id = b.card_id where part like %:part% and effect=:effect order by b.value desc, b.max desc", nativeQuery=true)
	List<Result> findBySubquery(@Param("part") String part, @Param("effect") String effect);

	@Query(value="SELECT * FROM card a Inner JOIN effect b ON a.id = b.card_id where a.id=:id", nativeQuery=true)
	List<Result> findByIdInJoinTable(@Param("id") int id);
	
	@Query(value="Select * from card a LEFT OUTER JOIN effect on a.id=effect.card_id where a.id>316 order by effect.effect desc", nativeQuery=true)
	List<Result> newCard();

}
