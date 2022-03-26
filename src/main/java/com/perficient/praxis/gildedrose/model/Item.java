package com.perficient.praxis.gildedrose.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "items")
public class Item {

	public String name;
	public int sellIn;
	public int quality;
	public Type type;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	public Item() {
	}

	public Item(int id, String name, int sellIn, int quality, Type type) {
		this.id = id;
		this.name = name;
		this.sellIn = sellIn;
		this.quality = quality;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return this.id + ", " + this.name + ", " + this.sellIn + ", " + this.quality;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Item item)) return false;
		return id == item.id &&
			sellIn == item.sellIn &&
			quality == item.quality &&
			name.equals(item.name) &&
			type == item.type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, sellIn, quality, type);
	}

	public enum Type {
		AGED,
		NORMAL,
		LEGENDARY,
		TICKETS
	}
}
