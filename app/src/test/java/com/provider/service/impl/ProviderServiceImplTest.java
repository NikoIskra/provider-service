package com.provider.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.provider.exception.BadRequestException;
import com.provider.model.ProviderGetAllReturnModel;
import com.provider.model.ProviderPostRequestModel;
import com.provider.model.ProviderPostReturnModel;
import com.provider.model.ProviderPostReturnModelResult;
import com.provider.model.ProviderUpdateRequestModel;
import com.provider.model.StatusEnum;
import com.provider.persistence.entity.Provider;
import com.provider.persistence.repository.ProviderRepository;
import com.provider.service.EntityConverterService;
import com.provider.service.ProviderValidator;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@ExtendWith(MockitoExtension.class)
public class ProviderServiceImplTest {
  @Mock ProviderRepository providerRepository;

  @Mock ProviderValidator providerValidator;

  @Mock EntityConverterService entityConverter;

  @InjectMocks ProviderServiceImpl providerServiceImpl;

  private static final UUID uuid = UUID.fromString("ec73eca8-1e43-4c0d-b5a7-588b3c0e3c9c");

  private static ProviderPostRequestModel createProviderRequestModel() {
    ProviderPostRequestModel providerRequestModel =
        new ProviderPostRequestModel("testname", "testtitle", "1234567890");
    return providerRequestModel;
  }

  private static ProviderPostRequestModel createInvalidProviderRequestModel() {
    ProviderPostRequestModel providerRequestModel =
        new ProviderPostRequestModel("testname", "testtitle", "12345678");
    return providerRequestModel;
  }

  private static Provider createProvider() {
    Provider provider = new Provider(uuid, "name", "title", "1234567890", StatusEnum.VIEW_ONLY);
    return provider;
  }

  private static ProviderPostReturnModel createProviderReturnModel() {
    ProviderPostReturnModelResult providerReturnModelResult =
        new ProviderPostReturnModelResult()
            .id(1L)
            .name("testname")
            .ownerId(uuid)
            .status(StatusEnum.VIEW_ONLY)
            .title("testtitle")
            .phoneNumber("1234567890");
    return new ProviderPostReturnModel().ok(true).result(providerReturnModelResult);
  }

  private static ProviderUpdateRequestModel createProviderUpdateRequestModel() {
    return new ProviderUpdateRequestModel().description("testdesc");
  }

  @Test
  void testInsertProvider() {
    ProviderPostRequestModel providerRequestModel = createProviderRequestModel();
    Provider provider = createProvider();
    ProviderPostReturnModel providerReturnModel = createProviderReturnModel();
    doNothing().when(providerValidator).validateProviderRequest(uuid, providerRequestModel);
    when(entityConverter.convertProviderRequestModelToProvider(providerRequestModel))
        .thenReturn(provider);
    when(entityConverter.convertProviderToReturnModel(provider)).thenReturn(providerReturnModel);
    ProviderPostReturnModel providerReturnModel2 = providerServiceImpl.save(uuid, providerRequestModel);
    assertEquals(providerRequestModel.getName(), providerReturnModel2.getResult().getName());
    assertEquals(providerRequestModel.getTitle(), providerReturnModel2.getResult().getTitle());
    assertEquals(
        providerRequestModel.getPhoneNumber(), providerReturnModel2.getResult().getPhoneNumber());
    verify(entityConverter).convertProviderRequestModelToProvider(providerRequestModel);
    verify(entityConverter).convertProviderToReturnModel(provider);
    verify(providerValidator).validateProviderRequest(uuid, providerRequestModel);
    verify(providerRepository).saveAndFlush(any());
  }

  @Test
  void testInsertProvider_validatorException() {
    ProviderPostRequestModel providerRequestModel = createInvalidProviderRequestModel();
    doThrow(new BadRequestException(null))
        .when(providerValidator)
        .validateProviderRequest(uuid, providerRequestModel);
    assertThrows(
        BadRequestException.class, () -> providerServiceImpl.save(uuid, providerRequestModel));
    verify(providerValidator).validateProviderRequest(uuid, providerRequestModel);
    verifyNoInteractions(providerRepository, entityConverter);
  }

  @Test
  void testPatchProvider() {
    Provider provider = createProvider();
    ProviderUpdateRequestModel providerUpdateRequestModel = createProviderUpdateRequestModel();
    doNothing()
        .when(providerValidator)
        .validateProviderPatchRequest(uuid, 1L, providerUpdateRequestModel);
    when(providerRepository.getById(any())).thenReturn(provider);
    ProviderPostReturnModel providerReturnModel =
        providerServiceImpl.patch(uuid, 1L, providerUpdateRequestModel);
    verify(entityConverter).patchRequestModelToProvider(providerUpdateRequestModel, provider);
    verify(providerRepository).save(provider);
  }

  @Test
  void testPatchProvider_validatorException() {
    ProviderUpdateRequestModel providerUpdateRequestModel = createProviderUpdateRequestModel();
    doThrow(new BadRequestException(null))
        .when(providerValidator)
        .validateProviderPatchRequest(uuid, 1L, providerUpdateRequestModel);
    assertThrows(
        BadRequestException.class,
        () -> providerServiceImpl.patch(uuid, 1L, providerUpdateRequestModel));
    verify(providerValidator).validateProviderPatchRequest(uuid, 1L, providerUpdateRequestModel);
    verifyNoInteractions(providerRepository, entityConverter);
  }

  @Test
  void testGetAllProviders() {
    Provider provider = createProvider();
    List<Provider> providers = List.of(provider);
    Page<Provider> providersPage = new PageImpl<>(providers);
    when(providerRepository.findAllWithChildren(any())).thenReturn(providersPage);
    ProviderGetAllReturnModel providerGetAllReturnModel = providerServiceImpl.getAll(uuid, 0, 50);
    assertEquals(providerGetAllReturnModel.isOk(), true);
    assertEquals(providerGetAllReturnModel.getResult().getPage(), 0);
    assertEquals(providerGetAllReturnModel.getResult().getPageSize(), 50);
    verify(providerRepository).findAllWithChildren(any());
    verify(providerValidator).validateProviderGetRequest(uuid);
    verify(entityConverter).convertProviderToGetDataObjects(anyList(), anyList(), anyList());
  }

  @Test
  void testGetAllProviders_validatorException() {
    doThrow(new BadRequestException(null)).when(providerValidator).validateProviderGetRequest(uuid);
    assertThrows(BadRequestException.class, () -> providerServiceImpl.getAll(uuid, 0, 50));
    verify(providerValidator).validateProviderGetRequest(uuid);
    verifyNoInteractions(providerRepository, entityConverter);
  }
}
