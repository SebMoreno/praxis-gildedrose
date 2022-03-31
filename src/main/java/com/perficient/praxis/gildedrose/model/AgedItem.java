package com.perficient.praxis.gildedrose.model;

import static com.perficient.praxis.gildedrose.utils.Constant.AGED_ITEM_QUALITY_BASE_CHANGE_RATE;
import static com.perficient.praxis.gildedrose.utils.Constant.MINIMUM_SELL_DAYS;

public class AgedItem extends Item {

	public AgedItem(int id, String name, int sellIn, int quality) {
		super(id, name, sellIn, quality, Type.AGED);
	}

	@Override
	public void calculateNewQuality() {
		quality += sellIn > MINIMUM_SELL_DAYS ?
			AGED_ITEM_QUALITY_BASE_CHANGE_RATE :
			2 * AGED_ITEM_QUALITY_BASE_CHANGE_RATE;
	}
}
