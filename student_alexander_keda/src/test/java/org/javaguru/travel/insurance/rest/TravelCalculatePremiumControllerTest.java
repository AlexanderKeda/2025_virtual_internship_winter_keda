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

    @Test
    void shouldReturnSuccessResponse() throws Exception {
        String requestJson = jsonFileReader
                .readJsonFromFile("/rest/TravelCalculatePremiumRequest_success.json");
        String expectedResponseJson = jsonFileReader
                .readJsonFromFile("/rest/TravelCalculatePremiumResponse_success.json");

        mockMvc.perform(MockMvcRequestBuilders.post("/insurance/travel/")
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }

    @Test
    void shouldReturnErrorWhenFirstNameIsEmpty() throws Exception {
        String requestJson = jsonFileReader
                .readJsonFromFile("/rest/TravelCalculatePremiumRequest_firstName_not_provided.json");
        String expectedResponseJson = jsonFileReader
                .readJsonFromFile("/rest/TravelCalculatePremiumResponse_firstName_not_provided.json");

        mockMvc.perform(MockMvcRequestBuilders.post("/insurance/travel/")
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }

    @Test
    void shouldReturnErrorWhenLastNameIsEmpty() throws Exception {
        String requestJson = jsonFileReader
                .readJsonFromFile("/rest/TravelCalculatePremiumRequest_lastName_not_provided.json");
        String expectedResponseJson = jsonFileReader
                .readJsonFromFile("/rest/TravelCalculatePremiumResponse_lastName_not_provided.json");

        mockMvc.perform(MockMvcRequestBuilders.post("/insurance/travel/")
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }

    @Test
    void shouldReturnErrorWhenDateFromIsEmpty() throws Exception {
        String requestJson = jsonFileReader
                .readJsonFromFile("/rest/TravelCalculatePremiumRequest_dateFrom_not_provided.json");
        String expectedResponseJson = jsonFileReader
                .readJsonFromFile("/rest/TravelCalculatePremiumResponse_dateFrom_not_provided.json");

        mockMvc.perform(MockMvcRequestBuilders.post("/insurance/travel/")
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }

    @Test
    void shouldReturnErrorWhenDateToIsEmpty() throws Exception {
        String requestJson = jsonFileReader
                .readJsonFromFile("/rest/TravelCalculatePremiumRequest_dateTo_not_provided.json");
        String expectedResponseJson = jsonFileReader
                .readJsonFromFile("/rest/TravelCalculatePremiumResponse_dateTo_not_provided.json");

        mockMvc.perform(MockMvcRequestBuilders.post("/insurance/travel/")
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }

    @Test
    void shouldReturnErrorsWhenAllFieldsAreEmpty() throws Exception {
        String requestJson = jsonFileReader
                .readJsonFromFile("/rest/TravelCalculatePremiumRequest_allFields_not_provided.json");
        String expectedResponseJson = jsonFileReader
                .readJsonFromFile("/rest/TravelCalculatePremiumResponse_allFields_not_provided.json");

        mockMvc.perform(MockMvcRequestBuilders.post("/insurance/travel/")
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }

    @Test
    void shouldReturnErrorWhenDateToIsBeforeDateFrom() throws Exception {
        String requestJson = jsonFileReader
                .readJsonFromFile("/rest/TravelCalculatePremiumRequest_dateTo_lessThen_dateFrom.json");
        String expectedResponseJson = jsonFileReader
                .readJsonFromFile("/rest/TravelCalculatePremiumResponse_dateTo_lessThen_dateFrom.json");

        mockMvc.perform(MockMvcRequestBuilders.post("/insurance/travel/")
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }
}