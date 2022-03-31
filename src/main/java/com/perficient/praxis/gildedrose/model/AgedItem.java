package com.perficient.praxis.gildedrose.model;

import static com.perficient.praxis.gildedrose.utils.Constant.*;

public class AgedItem extends Item {

	public AgedItem(int id, String name, int sellIn, int quality) {
		super(id, name, sellIn, quality, Type.AGED);
	}

	@Override
	public Item updateQuality() {
		if (sellIn > EXPIRYDATE) {
			quality += 1*POINTOFQUALITY;
		}
		else {
			quality += 2*POINTOFQUALITY;
		}
		if (quality > MAXIMUMQUALITY) {
			quality = MAXIMUMQUALITY;
		}
		sellIn-=DAYSTOEXPIRE;
		return this;
	}
}
