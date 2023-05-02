package org.scalefocus.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.scalefocus.model.Business;
import org.scalefocus.service.BusinessService;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.scalefocus.enums.Type.BAR;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
public class BusinessControllerTest {
    private MockMvc mockMvc;

    @Mock
    private BusinessService businessService;

    @InjectMocks
    private BusinessController businessController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(businessController)
                .build();
    }

    @Test
    public void getAllBusinesses_singleBusiness_success() throws Exception {
        Business business = new Business(1, "test", BAR, 1, "sofia",
                "0881234567", "test@gmail.com");
        when(businessService.getAllBusinesses()).thenReturn(Collections.singletonList(business));

        mockMvc.perform(get("/business"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(business.getId()))
                .andExpect(jsonPath("$[0].name").value(business.getName()))
                .andExpect(jsonPath("$[0].rating").value(business.getRating()))
                .andExpect(jsonPath("$[0].city").value(business.getCity()))
                .andExpect(jsonPath("$[0].phone").value(business.getPhone()))
                .andExpect(jsonPath("$[0].email").value(business.getEmail()));
    }
}