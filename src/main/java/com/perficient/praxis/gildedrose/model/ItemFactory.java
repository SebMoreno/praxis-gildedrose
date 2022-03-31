package com.perficient.praxis.gildedrose.model;


public class ItemFactory {
	private ItemFactory() {
	}

	public static Item newTypedInstance(int id, String name, int sellIn, int quality, Item.Type type) {
		return switch (type) {
			case NORMAL -> new Item(id, name, sellIn, quality, Item.Type.NORMAL);
			case AGED -> new AgedItem(id, name, sellIn, quality);
			case LEGENDARY -> new LegendaryItem(id, name, sellIn, quality);
			case TICKETS -> new TicketsItem(id, name, sellIn, quality);
		};
	}

	public static Item newTypedInstance(Item item) {
		return newTypedInstance(item.id, item.name, item.sellIn, item.quality, item.type);
	}

	public static Item newBaseItem(int id, String name, int sellIn, int quality, Item.Type type) {
		return new Item(id, name, sellIn, quality, type);
	}

	public static Item newBaseItem(Item item) {
		return newBaseItem(item.id, item.name, item.sellIn, item.quality, item.type);
	}
}
