package com.provider.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;

import com.provider.model.StatusEnum;
import com.provider.persistence.entity.Item;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@org.springframework.transaction.annotation.Transactional(propagation = Propagation.NOT_SUPPORTED)
@ActiveProfiles("test")
public class ItemRepositoryTest {
    
    @Autowired
    ItemRepository itemRepository;

    private static Item createItem() {
        Item item = new Item("testTitle", 12, StatusEnum.ACTIVE);
        return item;
    }

    @Test
    public void expectEmptyList() {
        List<Item> items = itemRepository.findAll();
        assertEquals(0, items.size());
    }

    @Test
    @DirtiesContext
    public void testInsertToDB() {
        itemRepository.save(createItem());
        List<Item> items = itemRepository.findAll();
        assertEquals(1, items.size());
    }

    @Test
    @DirtiesContext
    public void testDataPersists() {
        Item item = createItem();
        itemRepository.save(item);
        Item itemFromDB = itemRepository.findById(item.getId()).get();
        assertEquals(item.getTitle(), itemFromDB.getTitle());
        assertEquals(item.getPriceCents(), itemFromDB.getPriceCents());
        assertEquals(item.getStatus(), itemFromDB.getStatus());

    }

    private void insertEmptyItem() {
        Item item = new Item();
        itemRepository.save(item);
    }

    @Test
    public void testInsertEmptyItem() {
        Exception exception = assertThrows(
            DataIntegrityViolationException.class,
            () -> insertEmptyItem()
        );
    }
}
