package com.perficient.praxis.gildedrose.model;

import static com.perficient.praxis.gildedrose.utils.Constant.TICKET_ITEM_CLOSE_SELL_DAY;
import static com.perficient.praxis.gildedrose.utils.Constant.TICKET_ITEM_IMMINENT_SELL_DAY;
import static com.perficient.praxis.gildedrose.utils.Constant.TICKET_ITEM_QUALITY_BASE_CHANGE_RATE;
import static com.perficient.praxis.gildedrose.utils.Constant.TICKET_ITEM_STANDART_SELL_DAY;

public class TicketsItem extends Item {

	public TicketsItem(int id, String name, int sellIn, int quality) {
		super(id, name, sellIn, quality, Type.TICKETS);
	}

	@Override
	public void calculateNewQuality() {
		var qualityMultiplier = 0;
		if (sellIn > TICKET_ITEM_STANDART_SELL_DAY) {
			qualityMultiplier = 1;
		} else if (sellIn > TICKET_ITEM_CLOSE_SELL_DAY) {
			qualityMultiplier = 2;
		} else if (sellIn > TICKET_ITEM_IMMINENT_SELL_DAY) {
			qualityMultiplier = 3;
		}
		quality += qualityMultiplier * TICKET_ITEM_QUALITY_BASE_CHANGE_RATE;

		if (sellIn <= 0) {
			quality = 0;
		}
	}
}
