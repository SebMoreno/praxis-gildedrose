package com.perficient.praxis.gildedrose.business;

import com.perficient.praxis.gildedrose.error.ResourceNotFoundException;
import com.perficient.praxis.gildedrose.model.Item;
import com.perficient.praxis.gildedrose.repository.ItemRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ItemServiceTest {

    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;


    @Test
    public void testGetItemByIdWhenItemWasNotFound(){

        when(itemRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                itemService.findById(0));
    }

    @Test
    public void testGetItemByIdSuccess(){

        var item = new Item(0, "Oreo", 10, 30, Item.Type.NORMAL);
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(item));

        Item itemFound = itemService.findById(0);
        assertEquals(item, itemFound);
    }
    /**
     * GIVEN a valid normal type item in the database
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,
     * both will be decreased by 1
     */
    @Test
    public void testUpdateQualityOfNormalTypeItem(){

        var item = new Item(0, "Oreo", 10, 30, Item.Type.NORMAL);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Oreo", itemsUpdated.get(0).name);
        assertEquals(9, itemsUpdated.get(0).sellIn);
        assertEquals(29, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.NORMAL, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }
    /**
     * GIVEN a valid normal type item in the database.
     * WHEN its Sellin attribute decreases to less than zero.
     * THEN the Quality should decrease by two units.
     */
    @Test
    public void testQualityDecreasesTwiceFast() {
        var item = new Item(0, "Oreo", 0, 30, Item.Type.NORMAL);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Oreo", itemsUpdated.get(0).name);
        assertEquals(-1, itemsUpdated.get(0).sellIn);
        assertEquals(28, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.NORMAL, itemsUpdated.get(0).type);

        verify(itemRepository,times(1)).save(any());
    }

    /**
     * GIVEN that there is a valid article of normal type with zero quality in the database.
     * WHEN a day passes and the UpdateQuality method is called.
     * THEN the value of its quality must remain zero.
     */

    @Test
    public void testQualityNeverNegative() {
        var item = new Item(5, "tostada", 3, 0, Item.Type.NORMAL);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(5, itemsUpdated.get(0).getId());
        assertEquals("tostada", itemsUpdated.get(0).name);
        assertEquals(2, itemsUpdated.get(0).sellIn);
        assertEquals(0, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.NORMAL, itemsUpdated.get(0).type);

        verify(itemRepository,times(1)).save(any());
    }

    /**
     * GIVEN that there is a valid article of type AGED in the database.
     * WHEN a day passes and the UpdateQuality method is called.
     * THEN the value of its quality increases by one unit.
     */
    @Test
    public void testQualityOfAgedTypeQualityIncrease() {
        var item = new Item(6, "pastel", 3, 10, Item.Type.AGED);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(6, itemsUpdated.get(0).getId());
        assertEquals("pastel", itemsUpdated.get(0).name);
        assertEquals(2, itemsUpdated.get(0).sellIn);
        assertEquals(11, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.AGED, itemsUpdated.get(0).type);

        verify(itemRepository,times(1)).save(any());
    }

    /**
     * GIVEN a valid AGED type item in the database
     * WHEN a day passes and the UpdateQuality method is called.
     * THEN the item must be held at or below quality 50.
     *
     */

    @Test
    public void testQualityOfAgedTypeNeverOverFifty() {
        var item = new Item(8, "huevo", 3, 50, Item.Type.AGED);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(8, itemsUpdated.get(0).getId());
        assertEquals("huevo", itemsUpdated.get(0).name);
        assertEquals(2, itemsUpdated.get(0).sellIn);
        assertEquals(50, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.AGED, itemsUpdated.get(0).type);

        verify(itemRepository,times(1)).save(any());
    }

    /**
     * GIVEN a valid LEGENDARY type item in the database
     * WHEN a day passes and the UpdateQuality method is called.
     * THEN the Sellin and Quality attribute should not change value.
     *
     */

    @Test
    public void testQualityOfLegendaryTypeNeverSoldAndDegraded() {
        var item = new Item(10, "hamburguesa", 0, 80, Item.Type.LEGENDARY);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(10, itemsUpdated.get(0).getId());
        assertEquals("hamburguesa", itemsUpdated.get(0).name);
        assertEquals(0, itemsUpdated.get(0).sellIn);
        assertEquals(80, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.LEGENDARY, itemsUpdated.get(0).type);

        verify(itemRepository,times(1)).save(any());
    }

    /**
     * GIVEN an element of type TICKETS has SellIn greater than 11 days valid in the database.
     * WHEN a day passes and the UpdateQuality method is called.
     * THEN Quality should increase by one unit.
     */
    @Test
    public void testQualityOfTicketsTypeSellinOverEleven() {
        var item = new Item(12, "shakira", 15, 20, Item.Type.TICKETS);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(12, itemsUpdated.get(0).getId());
        assertEquals("shakira", itemsUpdated.get(0).name);
        assertEquals(14, itemsUpdated.get(0).sellIn);
        assertEquals(21, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.TICKETS, itemsUpdated.get(0).type);

        verify(itemRepository,times(1)).save(any());
    }

    /**
     * GIVEN an element of type TICKETS that has Sellin greater than 5 days and less
     * than or equal to 10 days valid in the database.
     * WHEN a day passes and the UpdateQuality method is called.
     * THEN his Quality should increase by two units.
     */

    @Test
    public void testQualityOfTicketsTypeSellinOverFiveAndUnderEleven() {
        var item = new Item(12, "shakira", 7, 20, Item.Type.TICKETS);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(12, itemsUpdated.get(0).getId());
        assertEquals("shakira", itemsUpdated.get(0).name);
        assertEquals(6, itemsUpdated.get(0).sellIn);
        assertEquals(22, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.TICKETS, itemsUpdated.get(0).type);

        verify(itemRepository,times(1)).save(any());
    }

    /**
     * GIVEN an element of type TICKETS that has Sellin less than
     * or equal to 5 days valid in the database.
     * WHEN a day passes and the UpdateQuality method is called.
     * THEN his Quality should increase by three units.
     */

    @Test
    public void testQualityOfTicketsTypeSellinUnderSix() {
        var item = new Item(13, "juanes", 5, 20, Item.Type.TICKETS);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(13, itemsUpdated.get(0).getId());
        assertEquals("juanes", itemsUpdated.get(0).name);
        assertEquals(4, itemsUpdated.get(0).sellIn);
        assertEquals(23, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.TICKETS, itemsUpdated.get(0).type);

        verify(itemRepository,times(1)).save(any());
    }

    /**
     * GIVEN an element of type TICKETS that has Sellin equal to 0 valid in the database.
     * WHEN a day passes and the UpdateQuality method is called.
     * THEN Quality should be equal to 0.
     */

    @Test
    public void testQualityOfTicketsTypeSellinEqualZero() {
        var item = new Item(13, "juanes", 0, 20, Item.Type.TICKETS);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(13, itemsUpdated.get(0).getId());
        assertEquals("juanes", itemsUpdated.get(0).name);
        assertEquals(-1, itemsUpdated.get(0).sellIn);
        assertEquals(0, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.TICKETS, itemsUpdated.get(0).type);

        verify(itemRepository,times(1)).save(any());
    }

    /**
     * GIVEN an item of type AGED that has Sellin less than 0 valid in the database.
     * WHEN a day passes and the UpdateQuality method is called.
     * THEN his Quality should increase by two units.
     */

    @Test
    public void testQualityOfAgedTypeSellinUnderZero() {
        var item = new Item(15, "tomate", -1, 20, Item.Type.AGED);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(15, itemsUpdated.get(0).getId());
        assertEquals("tomate", itemsUpdated.get(0).name);
        assertEquals(-2, itemsUpdated.get(0).sellIn);
        assertEquals(22, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.AGED, itemsUpdated.get(0).type);

        verify(itemRepository,times(1)).save(any());
    }

    @Test
    public void testListItemsSuccess(){
        var item = new Item(6, "pastel", 3, 10, Item.Type.AGED);
        List<Item> listSuccess = List.of(item);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> listItems = itemService.listItems();
        assertEquals(listSuccess, listItems);
    }

    @Test
    public void testUpdateItem(){
        /** Case 1.*/

        var item = new Item(0, "Oreo", 10, 30, Item.Type.NORMAL);

        when(itemRepository.existsById(any())).thenReturn(true);
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Item itemsAuxiliar = itemService.updateItem(0, item );

        assertEquals(item, itemsAuxiliar);

        /** Case 2.*/
        when(itemRepository.existsById(any())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () ->
                itemService.updateItem(0, item ));

    }
    @Test
    public void testCreateItem() {

        var item = new Item(0, "Oreo", 10, 30, Item.Type.NORMAL);

        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Item itemsAuxiliar = itemService.createItem(item);
        assertEquals(item, itemsAuxiliar);
    }









}
