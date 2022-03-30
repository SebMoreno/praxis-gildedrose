package com.perficient.praxis.gildedrose.model;

public class AgedItem extends Item {

	public AgedItem(int id, String name, int sellIn, int quality) {
		super(id, name, sellIn, quality, Type.AGED);
	}

	@Override
	public Item updateQuality() {
		System.out.println("update" + type);
		return this;
	}
}
