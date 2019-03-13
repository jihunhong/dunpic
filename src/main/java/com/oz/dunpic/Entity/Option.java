package com.oz.dunpic.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@IdClass(CompositeKey.class)
public class Option {
	
	@Id
	int card_id;
	
	@Id
	@Column(name="effect")
	String effect;
	
	@Column(name="groupname")
	String groupname;
	
	@Column(name="max")
	String max;

	@Column(name="upgrade")
	int upgrade;
	
	@Column(name="value")
	String value;
	
	@ManyToOne   //다대일(회원과 팀은 N:1 관계)
	@JoinColumn(name = "id")  //조인컬럼은 외래키를 매핑할때 사용 (연관관계주인)
	private Card card;  //연관관계 주인 필드

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	
	
	public int getCard_id() {
		return card_id;
	}

	public void setCard_id(int card_id) {
		this.card_id = card_id;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}


	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public int getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(int upgrade) {
		this.upgrade = upgrade;
	}
	

}
