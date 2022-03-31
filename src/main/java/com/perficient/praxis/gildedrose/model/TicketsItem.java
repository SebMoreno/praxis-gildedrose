package com.perficient.praxis.gildedrose.model;

import static com.perficient.praxis.gildedrose.utils.Constant.*;

public class TicketsItem extends Item {

	public TicketsItem(int id, String name, int sellIn, int quality) {
		super(id, name, sellIn, quality, Type.TICKETS);
	}

	@Override
	public Item updateQuality() {
		if (sellIn > 10*DAYSTOEXPIRE) {
			quality += 1*POINTOFQUALITY;
		}
		else if (sellIn <= 10*DAYSTOEXPIRE && sellIn > 5*DAYSTOEXPIRE){
			quality += 2*POINTOFQUALITY ;
		}
		else if (sellIn <= 5*DAYSTOEXPIRE && sellIn > EXPIRYDATE){
			quality += 3*POINTOFQUALITY;
		}
		else {
			quality = MINIMUMQUALITY;
		}
		if (quality > MAXIMUMQUALITY) {
			quality = MAXIMUMQUALITY;
		}
		sellIn-=DAYSTOEXPIRE;
		return this;

	}
}
