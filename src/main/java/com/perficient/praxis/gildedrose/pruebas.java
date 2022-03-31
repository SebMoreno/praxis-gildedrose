package com.perficient.praxis.gildedrose;

import com.perficient.praxis.gildedrose.model.Item;
import com.perficient.praxis.gildedrose.model.ItemFactory;


public class pruebas {
    public static void main(String[] args) {
        var item = ItemFactory.newInstance(0, "Oreo", 0, 30, Item.Type.NORMAL);
        var item2 = ItemFactory.newInstance(1, "pastel", 3, 10, Item.Type.AGED);
        var item3 = ItemFactory.newInstance(2, "huevo", 3, 50, Item.Type.AGED);
        var item4 = ItemFactory.newInstance(3, "tomate", -1, 20, Item.Type.AGED);
        var item5 = ItemFactory.newInstance(4, "shakira", 15, 20, Item.Type.TICKETS);
        var item6 = ItemFactory.newInstance(5, "jbalvin", 7, 40, Item.Type.TICKETS);
        var item7 = ItemFactory.newInstance(6, "homero", 5, 49, Item.Type.TICKETS);
        var item8 = ItemFactory.newInstance(7, "rosalia", 5, 40, Item.Type.TICKETS);
        var item9 = ItemFactory.newInstance(8, "juanes", 0, 20, Item.Type.TICKETS);
        var item10 = ItemFactory.newInstance(9, "golosa", -1, 80, Item.Type.LEGENDARY);


        System.out.println(item.updateQuality());
        System.out.println(item2.updateQuality());
        System.out.println(item3.updateQuality());
        System.out.println(item4.updateQuality());
        System.out.println(item5.updateQuality());
        System.out.println(item6.updateQuality());
        System.out.println(item7.updateQuality());
        System.out.println(item8.updateQuality());
        System.out.println(item9.updateQuality());
        System.out.println(item10.updateQuality());
    }
}
