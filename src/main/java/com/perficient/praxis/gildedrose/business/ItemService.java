package com.perficient.praxis.gildedrose.business;

import com.perficient.praxis.gildedrose.error.RepeatedItemsException;
import com.perficient.praxis.gildedrose.error.ResourceNotFoundException;
import com.perficient.praxis.gildedrose.model.Item;
import com.perficient.praxis.gildedrose.model.ItemFactory;
import com.perficient.praxis.gildedrose.repository.ItemRepository;
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
				itemRepository.findAll().stream()
					.map(ItemFactory::newTypedInstance)
					.map(Item::updateQuality)
					.map(ItemFactory::newBaseItem)
					.toList()
		);
	}


	public Item createItem(Item item) {
		return itemRepository.save(item);
	}

	public Item updateItem(int id, Item item) {
		if (itemRepository.existsById(id)) {
			return itemRepository.save(ItemFactory.newBaseItem(id, item.name, item.sellIn, item.quality, item.type));
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
