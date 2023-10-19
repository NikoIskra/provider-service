package com.provider.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.provider.model.StatusEnum;
import com.provider.persistence.entity.SubItem;
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

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@org.springframework.transaction.annotation.Transactional(propagation = Propagation.NOT_SUPPORTED)
@ActiveProfiles("test")
public class SubItemRepositoryTest {
  @Autowired SubItemRepository subItemRepository;

  private static SubItem createSubItem() {
    SubItem subItem = new SubItem("testTitle", 12, StatusEnum.ACTIVE);
    return subItem;
  }

  @Test
  public void expectEmptyList() {
    List<SubItem> subItems = subItemRepository.findAll();
    assertEquals(0, subItems.size());
  }

  @Test
  @DirtiesContext
  public void testInsertToDB() {
    subItemRepository.save(createSubItem());
    List<SubItem> subItems = subItemRepository.findAll();
    assertEquals(1, subItems.size());
  }

  @Test
  @DirtiesContext
  public void testDataPersists() {
    SubItem subItem = createSubItem();
    subItemRepository.save(subItem);
    SubItem subItemFromDB = subItemRepository.findById(subItem.getId()).get();
    assertEquals(subItem.getTitle(), subItemFromDB.getTitle());
    assertEquals(subItem.getPriceCents(), subItemFromDB.getPriceCents());
    assertEquals(subItem.getStatus(), subItemFromDB.getStatus());
  }

  private void insertEmptySubIstem() {
    SubItem subItem = new SubItem();
    subItemRepository.save(subItem);
  }

  @Test
  public void testInsertEmptySubItem() {
    Exception exception =
        assertThrows(DataIntegrityViolationException.class, () -> insertEmptySubIstem());
  }
}
