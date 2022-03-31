package com.perficient.praxis.gildedrose.model;

public class TicketsItem extends Item {

	public TicketsItem(int id, String name, int sellIn, int quality) {
		super(id, name, sellIn, quality, Type.TICKETS);
	}

	@Override
	public Item updateQuality() {
		if (sellIn > 10) {
			quality += 1;
		}
		else if (sellIn <= 10 && sellIn > 5){
			quality += 2;
		}
		else if (sellIn <= 5 && sellIn > 0){
			quality += 3;
		}
		else {
			quality = 0;
		}
		if (quality > 50) {
			quality = 50;
		}
		sellIn--;
		/**
		System.out.println("update" + type);
		 */
		return this;

	}
}
