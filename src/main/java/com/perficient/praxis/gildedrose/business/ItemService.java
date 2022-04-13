package com.perficient.praxis.gildedrose.business;

import com.perficient.praxis.gildedrose.error.RepeatedItemsException;
import com.perficient.praxis.gildedrose.error.ResourceNotFoundException;
import com.perficient.praxis.gildedrose.model.Item;
import com.perficient.praxis.gildedrose.model.ItemFactory;
import com.perficient.praxis.gildedrose.repository.ItemRepository;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.caseSensitive;

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
		if(existItems(item)){
			throw new RepeatedItemsException("The item already exists in the database");
		}
		return itemRepository.save(item);
	}

	public List<Item> createItems(List<Item> items) {
		if(existItems(items.toArray(new Item[0]))){
			throw new RepeatedItemsException("At least one item already exists in the database");
		}
		return itemRepository.saveAll(items);
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
			() -> new ResourceNotFoundException("The item you are trying to find does not exist"));
	}

	public void deleteById(int id) {
		Item item = findById(id);
		itemRepository.delete(item);
	}

	public boolean existItems(Item... items){
		ExampleMatcher itemMatcher = ExampleMatcher.matching()
			.withIgnorePaths("id")
			.withMatcher("name", caseSensitive())
			.withMatcher("quality", caseSensitive())
			.withMatcher("sellin", caseSensitive())
			.withMatcher("type", caseSensitive());
		return Arrays.stream(items).anyMatch(item -> itemRepository.exists(Example.of(item, itemMatcher)));
	}
}
