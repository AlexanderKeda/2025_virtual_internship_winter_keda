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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static uk.org.webcompere.modelassert.json.JsonAssertions.assertJson;

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

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/insurance/travel/")
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        assertJson(jsonResponse)
                .where()
                .keysInAnyOrder()
                .arrayInAnyOrder()
                .isEqualTo(expectedResponseJson);
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
    void shouldReturnErrorWhenSelectedRisksIsEmpty() throws Exception {
        performAndCheck("/rest/TravelCalculatePremiumRequest_selectedRisks_not_provided.json",
                "/rest/TravelCalculatePremiumResponse_selectedRisks_not_provided.json");
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

    @Test
    void shouldReturnErrorWhenDateFromIsInThePast() throws Exception {
        performAndCheck("/rest/TravelCalculatePremiumRequest_dateFrom_in_the_past.json",
                "/rest/TravelCalculatePremiumResponse_dateFrom_in_the_past.json");
    }

    @Test
    void shouldReturnErrorWhenDateToIsInThePast() throws Exception {
        performAndCheck("/rest/TravelCalculatePremiumRequest_dateTo_in_the_past.json",
                "/rest/TravelCalculatePremiumResponse_dateTo_in_the_past.json");
    }

    @Test
    void shouldReturnErrorWhenFirstNameIsNull() throws Exception {
        performAndCheck("/rest/TravelCalculatePremiumRequest_firstName_is_null.json",
                "/rest/TravelCalculatePremiumResponse_firstName_is_null.json");
    }

    @Test
    void shouldReturnErrorWhenLastNameIsNull() throws Exception {
        performAndCheck("/rest/TravelCalculatePremiumRequest_lastName_is_null.json",
                "/rest/TravelCalculatePremiumResponse_lastName_is_null.json");
    }

    @Test
    void shouldReturnErrorWhenRiskDoesNotExist() throws Exception {
        performAndCheck("/rest/TravelCalculatePremiumRequest_selectedRisks_does_not_exist.json",
                "/rest/TravelCalculatePremiumResponse_selectedRisks_does_not_exist.json");
    }

    @Test
    void shouldReturnErrorWhenRisksIsNull() throws Exception {
        performAndCheck("/rest/TravelCalculatePremiumRequest_selectedRisks_is_null.json",
                "/rest/TravelCalculatePremiumResponse_selectedRisks_is_null.json");
    }

    @Test
    void shouldReturnErrorWhenCountryDoesNotExist() throws Exception {
        performAndCheck("/rest/TravelCalculatePremiumRequest_country_does_not_exist.json",
                "/rest/TravelCalculatePremiumResponse_country_does_not_exist.json");
    }

    @Test
    void shouldReturnErrorWhenCountryIsNull() throws Exception {
        performAndCheck("/rest/TravelCalculatePremiumRequest_country_is_null.json",
                "/rest/TravelCalculatePremiumResponse_country_is_null.json");
    }

    @Test
    void shouldReturnErrorWhenCountryIsEmpty() throws Exception {
        performAndCheck("/rest/TravelCalculatePremiumRequest_country_is_null.json",
                "/rest/TravelCalculatePremiumResponse_country_is_null.json");
    }

    @Test
    void shouldReturnErrorWhenBirthDateIsNull() throws Exception {
        performAndCheck("/rest/TravelCalculatePremiumRequest_birthDate_is_null.json",
                "/rest/TravelCalculatePremiumResponse_birthDate_is_null.json");
    }

    @Test
    void shouldReturnErrorWhenBirthDateIsInTheFuture() throws Exception {
        performAndCheck("/rest/TravelCalculatePremiumRequest_birthDate_in_the_future.json",
                "/rest/TravelCalculatePremiumResponse_birthDate_in_the_future.json");
    }

    @Test
    void shouldReturnErrorWhenBirthDateExceedsLimit() throws Exception {
        performAndCheck("/rest/TravelCalculatePremiumRequest_birthDate_exceeds_limit.json",
                "/rest/TravelCalculatePremiumResponse_birthDate_exceeds_limit.json");
    }
}