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
    private MockMvc mockMvc;

    @Test
    void shouldResponseCorrect() throws Exception {
        String requestJson = """
                {
                "personFirstName" : "Ivan",
                "personLastName" : "Ivanov",
                "agreementDateFrom" : "2025-02-16",
                "agreementDateTo" : "2025-02-25"
                }
                """;
        String expectedResponseJson = """
                 {
                 "errors": null,
                 "personFirstName": "Ivan",
                 "personLastName": "Ivanov",
                 "agreementDateFrom": "2025-02-16",
                 "agreementDateTo": "2025-02-25",
                 "agreementPrice": 9
                 }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/insurance/travel/")
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }

    @Test
    void shouldReturnErrorWhenFirstNameIsEmpty() throws Exception {
        String requestJson = """
                {
                "personFirstName" : "",
                "personLastName" : "Ivanov",
                "agreementDateFrom" : "2025-02-16",
                "agreementDateTo" : "2025-02-25"
                }
                """;
        String expectedResponseJson = """
                 {
                                     "errors": [
                                         {
                                             "field": "personFirstName",
                                             "message": "Must not be empty!"
                                         }
                                     ],
                                     "personFirstName": null,
                                     "personLastName": null,
                                     "agreementDateFrom": null,
                                     "agreementDateTo": null,
                                     "agreementPrice": null
                                 }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/insurance/travel/")
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }

    @Test
    void shouldReturnErrorWhenLastNameIsEmpty() throws Exception {
        String requestJson = """
                {
                "personFirstName" : "Ivan",
                "personLastName" : "",
                "agreementDateFrom" : "2025-02-16",
                "agreementDateTo" : "2025-02-25"
                }
                """;
        String expectedResponseJson = """
                 {
                                    "errors": [
                                        {
                                            "field": "personLastName",
                                            "message": "Must not be empty!"
                                        }
                                    ],
                                    "personFirstName": null,
                                    "personLastName": null,
                                    "agreementDateFrom": null,
                                    "agreementDateTo": null,
                                    "agreementPrice": null
                                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/insurance/travel/")
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }

    @Test
    void shouldReturnErrorWhenDateFromIsEmpty() throws Exception {
        String requestJson = """
                {
                "personFirstName" : "Ivan",
                "personLastName" : "Ivanov",
                "agreementDateFrom" : "",
                "agreementDateTo" : "2025-02-25"
                }
                """;
        String expectedResponseJson = """
                 {
                                    "errors": [
                                        {
                                            "field": "agreementDateFrom",
                                            "message": "Must not be empty!"
                                        }
                                    ],
                                    "personFirstName": null,
                                    "personLastName": null,
                                    "agreementDateFrom": null,
                                    "agreementDateTo": null,
                                    "agreementPrice": null
                                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/insurance/travel/")
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }

    @Test
    void shouldReturnErrorWhenDateToIsEmpty() throws Exception {
        String requestJson = """
                {
                "personFirstName" : "Ivan",
                "personLastName" : "Ivanov",
                "agreementDateFrom" : "2025-02-16",
                "agreementDateTo" : ""
                }
                """;
        String expectedResponseJson = """
                 {
                                     "errors": [
                                         {
                                             "field": "agreementDateTo",
                                             "message": "Must not be empty!"
                                         }
                                     ],
                                     "personFirstName": null,
                                     "personLastName": null,
                                     "agreementDateFrom": null,
                                     "agreementDateTo": null,
                                     "agreementPrice": null
                                 }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/insurance/travel/")
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }

    @Test
    void shouldReturnErrorsWhenAllFieldsAreEmpty() throws Exception {
        String requestJson = """
                {
                "personFirstName" : "",
                "personLastName" : "",
                "agreementDateFrom" : "",
                "agreementDateTo" : ""
                }
                """;
        String expectedResponseJson = """
                 {
                                     "errors": [
                                         {
                                             "field": "personFirstName",
                                             "message": "Must not be empty!"
                                         },
                                         {
                                             "field": "personLastName",
                                             "message": "Must not be empty!"
                                         },
                                         {
                                             "field": "agreementDateFrom",
                                             "message": "Must not be empty!"
                                         },
                                         {
                                             "field": "agreementDateTo",
                                             "message": "Must not be empty!"
                                         }
                                     ],
                                     "personFirstName": null,
                                     "personLastName": null,
                                     "agreementDateFrom": null,
                                     "agreementDateTo": null,
                                     "agreementPrice": null
                                 }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/insurance/travel/")
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }

    @Test
    void shouldReturnErrorWhenDateToIsBeforeDateFrom() throws Exception {
        String requestJson = """
                {
                "personFirstName" : "Ivan",
                "personLastName" : "Ivanov",
                "agreementDateFrom" : "2025-02-25",
                "agreementDateTo" : "2025-02-16"
                }
                """;
        String expectedResponseJson = """
                 {
                                     "errors": [
                                         {
                                             "field": "agreementDateTo",
                                             "message": "Must be after DataFrom!"
                                         }
                                     ],
                                     "personFirstName": null,
                                     "personLastName": null,
                                     "agreementDateFrom": null,
                                     "agreementDateTo": null,
                                     "agreementPrice": null
                                 }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/insurance/travel/")
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }
}