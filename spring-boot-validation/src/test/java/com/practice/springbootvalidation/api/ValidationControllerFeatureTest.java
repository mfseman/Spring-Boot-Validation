package com.practice.springbootvalidation.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.springbootvalidation.models.request.PlayerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ValidationControllerFeatureTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void playerLookup_returns200Response() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/validation/player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPlayerRequest()))
                        .header("Authorization", "taco"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Billy"))
                .andExpect(jsonPath("$.lastName").value("Bob"))
                .andExpect(jsonPath("$.team").value("Blue"));
    }

    @Test
    void playerLookup_returns400Response_whenAuthorizationHeaderRegexIsInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/validation/player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPlayerRequest()))
                        .header("Authorization", "Mooo"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid Password"));
    }

    @Test
    void playerLookup_returns405Response_whenMethodDoesntExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/validation/player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPlayerRequest()))
                        .header("Authorization", "Mooo"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.message").value("Request method 'DELETE' not supported"));
    }

    @Test
    void playerLookup_returns415Response_whenMediaTypeIsInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/validation/player")
                        .contentType(MediaType.APPLICATION_XML)
                        .content(objectMapper.writeValueAsString(createPlayerRequest()))
                        .header("Authorization", "Mooo"))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(jsonPath("$.message").value("Content type 'application/xml' not supported"));
    }

    @Test
    void playerLookup_returns400Response_whenFirstNameInRequestBodyIsInvalidSize() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/validation/player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PlayerRequest.builder()
                                .firstName("FirstNameExceedsTwentyCharacters").build()))
                        .header("Authorization", "taco"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Your first name is too large"));
    }

    @Test
    void playerLookup_returns400Response_whenFirstNameInRequestBodyIsEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/validation/player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PlayerRequest.builder()
                                .firstName(null).build()))
                        .header("Authorization", "taco"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Your first name cannot be empty"));
    }

    @Test
    void playerLookup_returns400Response_whenFirstNameInRequestBodyIsInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/validation/player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PlayerRequest.builder()
                                .firstName("ashd872h23s8h22#$%@!").build()))
                        .header("Authorization", "taco"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Your first name is invalid"));
    }

    private PlayerRequest createPlayerRequest() {
        return PlayerRequest.builder().firstName("Bob").build();
    }
}
