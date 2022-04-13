package com.perficient.praxis.gildedrose.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

import static com.perficient.praxis.gildedrose.utils.Constant.MAXIMUM_QUALITY;
import static com.perficient.praxis.gildedrose.utils.Constant.MINIMUM_QUALITY;
import static com.perficient.praxis.gildedrose.utils.Constant.MINIMUM_SELL_DAYS;
import static com.perficient.praxis.gildedrose.utils.Constant.NORMAL_ITEM_QUALITY_BASE_CHANGE_RATE;
import static com.perficient.praxis.gildedrose.utils.Constant.SELLIN_CHANGE_RATE;


@Entity
@Table(name = "items")
@Data
public class Item {

	@NotBlank(message = "Name is mandatory")
	public String name;
	@NotNull(message = "sellIn is mandatory")
	public Integer sellIn;

	@NotNull(message = "quality is mandatory")
	@Min(0)
	@Max(80)
	public Integer quality;

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
		calculateNewQuality();
		ensureQualityBoundaries();
		sellIn += SELLIN_CHANGE_RATE;
		return this;
	}

	public void calculateNewQuality() {
		quality += sellIn > MINIMUM_SELL_DAYS ? NORMAL_ITEM_QUALITY_BASE_CHANGE_RATE : 2 * NORMAL_ITEM_QUALITY_BASE_CHANGE_RATE;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Item item)) return false;
		return sellIn.equals(item.sellIn) && quality.equals(item.quality) && id == item.id && name.equals(item.name) && type == item.type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, sellIn, quality, type, id);
	}

	protected void ensureQualityBoundaries() {
		if (quality < MINIMUM_QUALITY) {
			quality = MINIMUM_QUALITY;
		}
		if (quality > MAXIMUM_QUALITY) {
			quality = MAXIMUM_QUALITY;
		}
	}

	public enum Type {
		AGED, NORMAL, LEGENDARY, TICKETS
	}
}
