package com.provider.controller.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.provider.model.ErrorResponse;
import com.provider.model.ProviderRequestModel;
import com.provider.model.ProviderReturnModel;
import com.provider.model.ProviderReturnModelResult;
import com.provider.persistence.repository.ProviderRepository;
import com.provider.service.ProviderValidator;
import com.provider.service.impl.ProviderServiceImpl;

@WebMvcTest(ProviderController.class)
@EnableWebMvc
public class ProviderControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
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

    private static ProviderReturnModel createProviderReturnModel() {
        ProviderReturnModelResult providerReturnModelResult = new ProviderReturnModelResult()
        .id(1L)
        .phoneNumber("1324567890")
        .ownerId(uuid)
        .name("testname")
        .title("testtitle")
        .status("view-only");
        ProviderReturnModel providerReturnModel = new ProviderReturnModel().ok(true).result(providerReturnModelResult);
        return providerReturnModel;
    }
    
    ObjectMapper mapper = new ObjectMapper();


    @Test
    void insertProvider() throws Exception {
        ProviderRequestModel providerRequestModel= createProviderRequestModel();
        ProviderReturnModel providerReturnModel = createProviderReturnModel();
        when(providerServiceImpl.save(uuid, providerRequestModel)).thenReturn(providerReturnModel);
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/provider")
                .header("X-ACCOUNT-ID", uuid.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(providerRequestModel))
        )
        .andExpect(status().isCreated())
        .andReturn();
        assertNotNull(result.getResponse().getContentAsString());
    }


    @Test
    void insertProviderBadRequest() throws Exception {
        ProviderRequestModel providerRequestModel= createInvalidProviderRequestModel();
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/provider")
                .header("X-ACCOUNT-ID", "d3d35b67-b8f9-464b-a0b5-39f526e1f5f2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(providerRequestModel))
        )
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isBadRequest())
        .andReturn();
        String content = result.getResponse().getContentAsString();
        ErrorResponse errorResponse = mapper.readValue(content, ErrorResponse.class);
        assertEquals(errorResponse.isOk(), false);
    }
}
