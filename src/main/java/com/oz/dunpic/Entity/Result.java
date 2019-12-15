package com.oz.dunpic.Entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(CompositeKey.class)
public class Result {
	

	int id;
	
	String cardname;
	
	String part;
	
	String item_id;

	@Id
	int card_id;
	
	@Id
	String effect;
	
	String groupname;
	
	String max;
	
	Integer upgrade;
	
	String value;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the cardname
	 */
	public String getCardname() {
		return cardname;
	}

	/**
	 * @param cardname the cardname to set
	 */
	public void setCardname(String cardname) {
		this.cardname = cardname;
	}

	/**
	 * @return the part
	 */
	public String getPart() {
		return part;
	}

	/**
	 * @param part the part to set
	 */
	public void setPart(String part) {
		this.part = part;
	}

	/**
	 * @return the item_id
	 */
	public String getItem_id() {
		return item_id;
	}

	/**
	 * @param item_id the item_id to set
	 */
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	

	/**
	 * @return the card_id
	 */
	public int getCard_id() {
		return card_id;
	}

	/**
	 * @param card_id the card_id to set
	 */
	public void setCard_id(int card_id) {
		this.card_id = card_id;
	}

	/**
	 * @return the effect
	 */
	public String getEffect() {
		return effect;
	}

	/**
	 * @param effect the effect to set
	 */
	public void setEffect(String effect) {
		this.effect = effect;
	}

	/**
	 * @return the groupname
	 */
	public String getGroupname() {
		return groupname;
	}

	/**
	 * @param groupname the groupname to set
	 */
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	/**
	 * @return the max
	 */
	public String getMax() {
		return max;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(String max) {
		this.max = max;
	}

	/**
	 * @return the upgrade
	 */
	public Integer getUpgrade() {
		return upgrade;
	}

	/**
	 * @param upgrade the upgrade to set
	 */
	public void setUpgrade(Integer upgrade) {
		this.upgrade = upgrade;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	public Result() {
	}

	public Result(int id, String cardname, String part, String item_id, int card_id, String effect, String groupname,
			String max, int upgrade, String value) {
		this.id = id;
		this.cardname = cardname;
		this.part = part;
		this.item_id = item_id;
		this.card_id = card_id;
		this.effect = effect;
		this.groupname = groupname;
		this.max = max;
		this.upgrade = upgrade;
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Result [id=" + id + ", cardname=" + cardname + ", part=" + part + ", item_id=" + item_id + ", option_id="
				+ card_id + ", effect=" + effect + ", groupname=" + groupname + ", max=" + max + ", upgrade="
				+ upgrade + ", value=" + value + "]";
	}

	

}
