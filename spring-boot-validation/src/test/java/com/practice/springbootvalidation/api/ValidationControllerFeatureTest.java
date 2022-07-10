package com.practice.springbootvalidation.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.springbootvalidation.errorhandling.ErrorHandlingControllerAdvice;
import com.practice.springbootvalidation.models.request.MultiplePlayersRequest;
import com.practice.springbootvalidation.models.request.PlayerRequest;
import com.practice.springbootvalidation.service.ValidateMultipleRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {
        ValidateMultipleRequestService.class,
        ValidationController.class,
        ErrorHandlingControllerAdvice.class
})
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

    @Test
    void multiplePLayerLookup_returnsSuccess() throws Exception {
        List<PlayerRequest> playerRequest = List.of(
                PlayerRequest.builder().firstName("Billy").build(),
                PlayerRequest.builder().firstName("Marco").build(),
                PlayerRequest.builder().firstName("Jack").build());

        MultiplePlayersRequest playerRequests = MultiplePlayersRequest.builder()
                .firstNames(playerRequest)
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/validation/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerRequests))
                        .header("Authorization", "taco"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teamPlayers[0].firstName").value("Billy"))
                .andExpect(jsonPath("$.teamPlayers[1].firstName").value("Marco"))
                .andExpect(jsonPath("$.teamPlayers[2].firstName").value("Jack"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void multiplePLayerLookup_returns207Response_whenThereAreSuccessfulAndFailureRequests() throws Exception {
        List<PlayerRequest> playerRequest = List.of(
                PlayerRequest.builder().firstName("Marco").build(),
                PlayerRequest.builder().firstName("Thisexceedstwentycharacters").build(),
                PlayerRequest.builder().firstName("2390je290js2jq9sw").build(),
                PlayerRequest.builder().firstName(null).build());

        MultiplePlayersRequest playerRequests = MultiplePlayersRequest.builder()
                .firstNames(playerRequest)
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/validation/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerRequests))
                        .header("Authorization", "taco"))
                .andExpect(status().isMultiStatus())
                .andExpect(jsonPath("$.teamPlayers[0].firstName").value("Marco"))
                .andExpect(jsonPath("$.teamPlayers[1].errorMessage").value("Your first name is too large"))
                .andExpect(jsonPath("$.teamPlayers[2].errorMessage").value("Your first name is invalid"))
                .andExpect(jsonPath("$.teamPlayers[3].errorMessage").value("Your first name cannot be empty"));
    }

    @Test
    void multiplePLayerLookup_returns400Response_whenListExceedsSizeOfFive() throws Exception {
        List<PlayerRequest> playerRequest = List.of(
                PlayerRequest.builder().firstName("Marco").build(),
                PlayerRequest.builder().firstName("Polo").build(),
                PlayerRequest.builder().firstName("Jack").build(),
                PlayerRequest.builder().firstName("Richard").build(),
                PlayerRequest.builder().firstName("Jefferson").build(),
                PlayerRequest.builder().firstName("Billy").build());

        MultiplePlayersRequest playerRequests = MultiplePlayersRequest.builder()
                .firstNames(playerRequest)
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/validation/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerRequests))
                        .header("Authorization", "taco"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("You can only provide less than 5 names"));
    }

    @Test
    void multiplePLayerLookup_returns400Response_whenListIsEmpty() throws Exception {
        List<PlayerRequest> playerRequest = new ArrayList<>();

        MultiplePlayersRequest playerRequests = MultiplePlayersRequest.builder()
                .firstNames(playerRequest)
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/validation/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerRequests))
                        .header("Authorization", "taco"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("First names are required"));
    }

    private PlayerRequest createPlayerRequest() {
        return PlayerRequest.builder().firstName("Billy").build();
    }
}
