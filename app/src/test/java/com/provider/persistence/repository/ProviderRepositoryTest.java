package com.provider.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

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
import com.provider.persistence.entity.Provider;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@org.springframework.transaction.annotation.Transactional(propagation = Propagation.NOT_SUPPORTED)
@ActiveProfiles("test")
public class ProviderRepositoryTest {

    @Autowired
    private ProviderRepository providerRepository;

    private static final UUID uuid = UUID.fromString("1805442b-3504-4e93-be8b-b87067377a24");

    private static Provider createProvider() {
        Provider provider = new Provider(uuid, "testName", "testTitle", "123456789", StatusEnum.ACTIVE);
        return provider;
    }

    @Test
    public void expectEmptyList() {
        List<Provider> providers = providerRepository.findAll();
        assertEquals(0, providers.size());
    }

    @Test
    @DirtiesContext
    public void testInsertToDB() {
        providerRepository.save(createProvider());
        List<Provider> providers = providerRepository.findAll();
        assertEquals(1, providers.size());
    }

    @Test
    @DirtiesContext
    public void testDataPersists() {
        Provider provider = createProvider();
        providerRepository.save(provider);
        Provider providerFromDB = providerRepository.findById(provider.getId()).get();
        assertEquals(provider.getName(), providerFromDB.getName());
        assertEquals(provider.getOwnerId(), providerFromDB.getOwnerId());
        assertEquals(provider.getTitle(), providerFromDB.getTitle());
        assertEquals(provider.getPhoneNumber(), providerFromDB.getPhoneNumber());
        assertEquals(provider.getStatus(), providerFromDB.getStatus());
    }

    private void insertEmptyProvider() {
        Provider provider = new Provider();
        providerRepository.save(provider);
    }

    @Test
    public void testInsertEmptyProvider() {
        Exception exception = assertThrows(
            DataIntegrityViolationException.class,
            () -> insertEmptyProvider()
        );
    }

}
