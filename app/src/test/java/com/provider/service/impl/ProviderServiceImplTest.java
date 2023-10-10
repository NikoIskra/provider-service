package com.provider.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.provider.model.ProviderRequestModel;
import com.provider.model.ProviderReturnModel;
import com.provider.persistence.repository.ProviderRepository;
import com.provider.service.ProviderValidator;

@ExtendWith(MockitoExtension.class)
public class ProviderServiceImplTest {
    @Mock
    ProviderRepository providerRepository;

    @Mock
    ProviderValidator providerValidator;

    @InjectMocks
    ProviderServiceImpl providerServiceImpl;

    private static final UUID uuid = UUID.fromString("ec73eca8-1e43-4c0d-b5a7-588b3c0e3c9c");

    private static ProviderRequestModel createProviderRequestModel() {
    ProviderRequestModel providerRequestModel = new ProviderRequestModel("testname", "testtitle", "1234567890");
    return providerRequestModel;
    }

    @Test
    void testInsertProvider() {
        ProviderRequestModel providerRequestModel = createProviderRequestModel();
        doNothing().when(providerValidator).validateProviderRequest(uuid, providerRequestModel);
        ProviderReturnModel providerReturnModel = providerServiceImpl.save(uuid, providerRequestModel);
        assertEquals(providerRequestModel.getName(), providerReturnModel.getResult().getName());
        assertEquals(providerRequestModel.getTitle(), providerReturnModel.getResult().getTitle());
        assertEquals(providerRequestModel.getPhoneNumber(), providerReturnModel.getResult().getPhoneNumber());
    }

}
