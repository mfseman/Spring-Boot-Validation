package com.practice.springbootvalidation.api;

import com.practice.springbootvalidation.models.request.PlayerRequest;
import com.practice.springbootvalidation.models.response.PlayerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/validation")
public class ValidationController {

    @PostMapping(path = "/player", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerResponse> playerLookup(
            @Pattern(regexp = "(password|taco)", message = "Invalid Password")
            @Size(min = 4, max = 8, message = "Invalid Size")
            @RequestHeader(value = "Authorization") String authorization,
            @Validated @RequestBody PlayerRequest playerRequest) {

        return ResponseEntity.status(HttpStatus.OK).body(PlayerResponse.builder().firstName("Billy").lastName("Bob").team("Blue").build());
    }

    @PostMapping(path = "/players", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerResponse> playersLookup(
            @Pattern(regexp = "(password|taco)", message = "Invalid Password")
            @Size(min = 4, max = 8, message = "Invalid Size")
            @RequestHeader(value = "Authorization") String authorization,
            @Validated @RequestBody PlayerRequest playerRequest) {

        return ResponseEntity.status(HttpStatus.OK).body(PlayerResponse.builder().firstName("Billy").lastName("Bob").team("Blue").build());
    }
}
