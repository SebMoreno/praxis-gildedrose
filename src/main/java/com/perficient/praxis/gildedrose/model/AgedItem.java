package com.perficient.praxis.gildedrose.model;

public class AgedItem extends Item {

	public AgedItem(int id, String name, int sellIn, int quality) {
		super(id, name, sellIn, quality, Type.AGED);
	}

	@Override
	public Item updateQuality() {
		if (sellIn > 0) {
			quality += 1;
		}
		else {
			quality += 2;
		}
		if (quality > 50) {
			quality = 50;
		}
		sellIn--;
		return this;
	}
}
