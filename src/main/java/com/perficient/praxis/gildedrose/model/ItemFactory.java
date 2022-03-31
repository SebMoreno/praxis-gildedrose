package com.perficient.praxis.gildedrose.model;


public class ItemFactory {
    private ItemFactory() {
    }
    public static Item newInstance(int id, String name, int sellIn, int quality, Item.Type type) {
        return switch (type) {
            case NORMAL -> new Item(id, name, sellIn, quality, Item.Type.NORMAL);
            case AGED -> new AgedItem(id, name, sellIn, quality);
            case LEGENDARY -> new LegendaryItem(id, name, sellIn, quality);
            case TICKETS -> new TicketsItem(id, name, sellIn, quality);
        };
    }
    public static Item newInstance(Item item) {
        return newInstance(item.id, item.name, item.sellIn, item.quality, item.type);
    }
}
