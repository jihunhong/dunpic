package com.oz.dunpic.Entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
public class SearchLog {

	@Id
	@GeneratedValue
	int id;
	
    String server;

    String characterId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date regDate;
    
}