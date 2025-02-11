package org.javaguru.travel.insurance.rest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TravelCalculatePremiumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void simpleRestControllerTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/insurance/travel/")
                .content("""
                        {
                        "personFirstName" : "Ivan",
                        "personLastName" : "Ivanov",
                        "agreementDateFrom" : "2025-02-11",
                        "agreementDateTo" : "2025-02-15"
                        }
                        """)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("personFirstName", Matchers.is("Ivan")))
                .andExpect(jsonPath("personLastName", Matchers.is("Ivanov")))
                .andExpect(jsonPath("agreementDateFrom", Matchers.is("2025-02-11")))
                .andExpect(jsonPath("agreementDateTo", Matchers.is("2025-02-15")))
                .andExpect(jsonPath("agreementPrice", Matchers.is(4)));
    }

}