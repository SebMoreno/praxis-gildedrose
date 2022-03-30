package com.perficient.praxis.gildedrose.model;

public class LegendaryItem extends Item {

	public LegendaryItem(int id, String name, int sellIn, int quality) {
		super(id, name, sellIn, quality, Item.Type.LEGENDARY);
	}

	@Override
	public Item updateQuality() {
		return this;
	}
}
