package com.oz.dunpic.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Card {
	
	@Id
	int id;

	String cardname;


	String part;

	String itemId;

	@OneToMany(mappedBy = "card")  // Member 객체와 양방향 관계를 만들기위해 추가한다. (대상테이블)
	private List<Option> card = new ArrayList<Option>();

	
	
	public List<Option> getCard() {
		return card;
	}



	public void setCards(List<Option> card) {
		this.card = card;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getCardname() {
		return cardname;
	}



	public void setCardname(String cardname) {
		this.cardname = cardname;
	}



	public String getPart() {
		return part;
	}



	public void setPart(String part) {
		this.part = part;
	}



	public String getItemId() {
		return itemId;
	}



	public void setItemId(String itemId) {
		this.itemId = itemId;
	}


	
	

	
}
