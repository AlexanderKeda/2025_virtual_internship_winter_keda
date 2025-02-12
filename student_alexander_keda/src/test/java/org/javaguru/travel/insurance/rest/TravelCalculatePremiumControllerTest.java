package org.javaguru.travel.insurance.rest;

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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TravelCalculatePremiumControllerTest {

    @Autowired
    JsonFileReader jsonFileReader;

    @Autowired
    private MockMvc mockMvc;

    private void performAndCheck(String requestPath, String expectedResponsePath) throws Exception {
        String requestJson = jsonFileReader.readJsonFromFile(requestPath);

        String expectedResponseJson = jsonFileReader.readJsonFromFile(expectedResponsePath);

        mockMvc.perform(MockMvcRequestBuilders.post("/insurance/travel/")
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }

    @Test
    void shouldReturnSuccessResponse() throws Exception {
        performAndCheck("/rest/TravelCalculatePremiumRequest_success.json",
                "/rest/TravelCalculatePremiumResponse_success.json");
    }


    @Test
    void shouldReturnErrorWhenFirstNameIsEmpty() throws Exception {
        performAndCheck("/rest/TravelCalculatePremiumRequest_firstName_not_provided.json",
                        "/rest/TravelCalculatePremiumResponse_firstName_not_provided.json");
    }

    @Test
    void shouldReturnErrorWhenLastNameIsEmpty() throws Exception {
        performAndCheck("/rest/TravelCalculatePremiumRequest_lastName_not_provided.json",
                "/rest/TravelCalculatePremiumResponse_lastName_not_provided.json");
    }

    @Test
    void shouldReturnErrorWhenDateFromIsEmpty() throws Exception {
        performAndCheck("/rest/TravelCalculatePremiumRequest_dateFrom_not_provided.json",
                "/rest/TravelCalculatePremiumResponse_dateFrom_not_provided.json");
    }

    @Test
    void shouldReturnErrorWhenDateToIsEmpty() throws Exception {
        performAndCheck("/rest/TravelCalculatePremiumRequest_dateTo_not_provided.json",
                "/rest/TravelCalculatePremiumResponse_dateTo_not_provided.json");
    }

    @Test
    void shouldReturnErrorsWhenAllFieldsAreEmpty() throws Exception {
        performAndCheck("/rest/TravelCalculatePremiumRequest_allFields_not_provided.json",
                "/rest/TravelCalculatePremiumResponse_allFields_not_provided.json");
    }

    @Test
    void shouldReturnErrorWhenDateToIsBeforeDateFrom() throws Exception {
        performAndCheck("/rest/TravelCalculatePremiumRequest_dateTo_lessThen_dateFrom.json",
                "/rest/TravelCalculatePremiumResponse_dateTo_lessThen_dateFrom.json");
    }
}