package com.perficient.praxis.gildedrose.model;

public class TicketsItem extends Item {

	public TicketsItem(int id, String name, int sellIn, int quality) {
		super(id, name, sellIn, quality, Type.TICKETS);
	}

	@Override
	public Item updateQuality() {
		System.out.println("update" + type);
		return this;
	}
}
