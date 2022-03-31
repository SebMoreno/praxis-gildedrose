package com.perficient.praxis.gildedrose.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import static com.perficient.praxis.gildedrose.utils.Constant.EXPIRATIONDAY;


@Entity
@Table(name = "items")
public class Item {

	public String name;
	public int sellIn;
	public int quality;
	public Type type;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected int id;

	public Item() {
	}

	protected Item(int id, String name, int sellIn, int quality, Type type) {
		this.id = id;
		this.name = name;
		this.sellIn = sellIn;
		this.quality = quality;
		this.type = type;
	}


	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return type + "Item{" + this.id + ", " + this.name + ", " + this.sellIn + ", " + this.quality + "}";
	}

	public Item updateQuality() {
		quality -= sellIn <= EXPIRATIONDAY ? 2 : 1;
		if (quality < 0) {
			quality = 0;
		}
		sellIn--;
		return this;
	}

	public enum Type {
		AGED,
		NORMAL,
		LEGENDARY,
		TICKETS
	}
}
