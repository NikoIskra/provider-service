package com.provider.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.aspectj.apache.bcel.classfile.Module.Provide;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.provider.exception.BadRequestException;
import com.provider.model.ProviderRequestModel;
import com.provider.model.ProviderReturnModel;
import com.provider.model.ProviderReturnModelResult;
import com.provider.model.ProviderUpdateRequestModel;
import com.provider.model.StatusEnum;
import com.provider.persistence.entity.Provider;
import com.provider.persistence.repository.ProviderRepository;
import com.provider.service.EntityConverterService;
import com.provider.service.ProviderValidator;

@ExtendWith(MockitoExtension.class)
public class ProviderServiceImplTest {
    @Mock
    ProviderRepository providerRepository;

    @Mock
    ProviderValidator providerValidator;

    @Mock
    EntityConverterService entityConverter;

    @InjectMocks
    ProviderServiceImpl providerServiceImpl;

    private static final UUID uuid = UUID.fromString("ec73eca8-1e43-4c0d-b5a7-588b3c0e3c9c");

    private static ProviderRequestModel createProviderRequestModel() {
        ProviderRequestModel providerRequestModel = new ProviderRequestModel("testname", "testtitle", "1234567890");
        return providerRequestModel;
    }

    private static ProviderRequestModel createInvalidProviderRequestModel() {
        ProviderRequestModel providerRequestModel = new ProviderRequestModel("testname", "testtitle", "12345678");
        return providerRequestModel;
    }

    private static Provider createProvider() {
        Provider provider = new Provider(uuid, "name", "title", "1234567890", StatusEnum.VIEW_ONLY);
        return provider;
    }

    private static ProviderReturnModel createProviderReturnModel() {
        ProviderReturnModelResult providerReturnModelResult = new ProviderReturnModelResult()
        .id(1L)
        .name("testname")
        .ownerId(uuid)
        .status(StatusEnum.VIEW_ONLY)
        .title("testtitle")
        .phoneNumber("1234567890");
        return new ProviderReturnModel().ok(true).result(providerReturnModelResult);
    }

        private static ProviderUpdateRequestModel createProviderUpdateRequestModel() {
        return new ProviderUpdateRequestModel()
        .description("testdesc");
    }


    @Test
    void testInsertProvider() {
        ProviderRequestModel providerRequestModel = createProviderRequestModel();
        Provider provider = createProvider();
        ProviderReturnModel providerReturnModel = createProviderReturnModel();
        doNothing().when(providerValidator).validateProviderRequest(uuid, providerRequestModel);
        when(entityConverter.convertProviderRequestModelToProvider(providerRequestModel)).thenReturn(provider);
        when(entityConverter.convertProviderToReturnModel(provider)).thenReturn(providerReturnModel);
        ProviderReturnModel providerReturnModel2 = providerServiceImpl.save(uuid, providerRequestModel);
        assertEquals(providerRequestModel.getName(), providerReturnModel2.getResult().getName());
        assertEquals(providerRequestModel.getTitle(), providerReturnModel2.getResult().getTitle());
        assertEquals(providerRequestModel.getPhoneNumber(), providerReturnModel2.getResult().getPhoneNumber());
        verify(entityConverter).convertProviderRequestModelToProvider(providerRequestModel);
        verify(entityConverter).convertProviderToReturnModel(provider);
        verify(providerValidator).validateProviderRequest(uuid, providerRequestModel);
        verify(providerRepository).saveAndFlush(any());
    }

    @Test
    void testInsertProvider_validatorException() {
        ProviderRequestModel providerRequestModel = createInvalidProviderRequestModel();
        doThrow(new BadRequestException(null)).when(providerValidator).validateProviderRequest(uuid,
                providerRequestModel);
        assertThrows(BadRequestException.class,
                () -> providerServiceImpl.save(uuid, providerRequestModel));
        verify(providerValidator).validateProviderRequest(uuid, providerRequestModel);
        verifyNoInteractions(providerRepository);
        verifyNoInteractions(entityConverter);
    }
    
    @Test
    void testPatchProvider() {
        Provider provider = createProvider();
        ProviderUpdateRequestModel providerUpdateRequestModel = createProviderUpdateRequestModel();
        doNothing().when(providerValidator).validateProviderPatchRequest(uuid, 1L, providerUpdateRequestModel);
        when(providerRepository.getById(any())).thenReturn(provider);
        provider.setDescription(providerUpdateRequestModel.getDescription());
        ProviderReturnModel providerReturnModel = providerServiceImpl.patch(uuid, 1L, providerUpdateRequestModel);
        verify(entityConverter).patchRequestModelToProvider(providerUpdateRequestModel, provider);
        verify(providerRepository).save(provider);
    }

    @Test
    void testPatchProvider_validatorException() {
        ProviderUpdateRequestModel providerUpdateRequestModel = createProviderUpdateRequestModel();
        doThrow(new BadRequestException(null)).when(providerValidator).validateProviderPatchRequest(uuid, 1L, providerUpdateRequestModel);
        assertThrows(BadRequestException.class,
        () -> providerServiceImpl.patch(uuid, 1L, providerUpdateRequestModel)
        );
        verify(providerValidator).validateProviderPatchRequest(uuid, 1L, providerUpdateRequestModel);
        verifyNoInteractions(providerRepository);
        verifyNoInteractions(entityConverter);
    }
}
