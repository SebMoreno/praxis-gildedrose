package com.perficient.praxis.gildedrose.business;

import com.perficient.praxis.gildedrose.error.RepeatedItemsException;
import com.perficient.praxis.gildedrose.error.ResourceNotFoundException;
import com.perficient.praxis.gildedrose.model.AgedItem;
import com.perficient.praxis.gildedrose.model.Item;
import com.perficient.praxis.gildedrose.model.ItemFactory;
import com.perficient.praxis.gildedrose.repository.ItemRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

	private final ItemRepository itemRepository;

	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	public List<Item> updateQuality() {
		return itemRepository.saveAll(
				itemRepository.findAll().stream().map(ItemFactory::newInstance).map(Item::updateQuality).toList()
		);


		/*for (Item item : items) {
			if (!item.type.equals(Item.Type.AGED)
				&& !item.type.equals(Item.Type.TICKETS)) {
				if (item.quality > 0) {
					if (!item.type.equals(Item.Type.LEGENDARY)) {
						item.quality = item.quality - 1;
					}
				}
			} else {
				if (item.quality < 50) {
					item.quality = item.quality + 1;

					if (item.type.equals(Item.Type.TICKETS)) {
						if (item.sellIn < 11) {
							if (item.quality < 50) {
								item.quality = item.quality + 1;
							}
						}

						if (item.sellIn < 6) {
							if (item.quality < 50) {
								item.quality = item.quality + 1;
							}
						}
					}
				}
			}

			if (!item.type.equals(Item.Type.LEGENDARY)) {
				item.sellIn = item.sellIn - 1;
			}

			if (item.sellIn < 0) {
				if (!item.type.equals(Item.Type.AGED)) {
					if (!item.type.equals(Item.Type.TICKETS)) {
						if (item.quality > 0) {
							if (!item.type.equals(Item.Type.LEGENDARY)) {
								item.quality = item.quality - 1;
							}
						}
					} else {
						item.quality = 0;
					}
				} else {
					if (item.quality < 50) {
						item.quality = item.quality + 1;
					}
				}
			}
			itemRepository.save(item);
		}
		return items;*/
	}


	public Item createItem(Item item) {
		return itemRepository.save(item);
	}

	public Item updateItem(int id, Item item) {
		if (itemRepository.existsById(id)) {
			return itemRepository.save(ItemFactory.newInstance(id, item.name, item.sellIn, item.quality, item.type));
		} else {
			throw new ResourceNotFoundException("The item you are trying to update does not exist");
		}
	}

	public List<Item> listItems() {
		return itemRepository.findAll();
	}

	public Item findById(int id) {
		return itemRepository.findById(id).orElseThrow(
			() -> new ResourceNotFoundException(""));
	}

	public List<Item> createItems(List<Item> items) {
		if (items.stream().distinct().count() == items.size()) {
			return itemRepository.saveAll(items);
		}
		throw new RepeatedItemsException("There's at least one repeted item in the list provided");
	}
}
