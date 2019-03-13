package com.oz.dunpic.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oz.dunpic.Entity.Card;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;




public interface CardDAO extends JpaRepository<Card, Integer>{
	
	@Query("Select p from Card p where cardname = :cardname")
	Card findByCardname(@Param("cardname") String cardname);
	
	@Query("Select DISTINCT groupname from Option  where card_id in (select id from Card where part like %:part% ) order by groupname ASC")
	List<Card> findByPartForGroup(@Param("part") String part);
	
	@Query("Select DISTINCT effect from Option  where card_id in (select id from Card where part like %:part% ) and groupname = :groupname order by effect DESC")
	List<Card> findByGroupForEffect(@Param("part") String part, @Param("groupname") String groupname);
	
	/*  SELECT distinct effect
		FROM dunpic.card 	
		where part like '%part%' and groupname = 'groupname';
		group by effect
	
		UNION
		
		SELECT distinct effect_nd
		FROM dunpic.card 
		where part like '%p%'
		group by effect_nd;*/
	
	//@Query("select p from Card p where part like %:part% and effect=:effect order by value DESC")
	//@Query("select b from Card a, Option b where a.id=b.id and a.part like %:part% and b.effect= :effect order by b.value desc")
	
}
