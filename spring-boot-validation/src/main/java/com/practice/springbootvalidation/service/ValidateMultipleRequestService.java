package com.practice.springbootvalidation.service;

import com.practice.springbootvalidation.models.request.MultiplePlayersRequest;
import com.practice.springbootvalidation.models.request.PlayerRequest;
import com.practice.springbootvalidation.models.response.CustomErrorDetailException;
import com.practice.springbootvalidation.models.response.MultiplePlayersResponse;
import com.practice.springbootvalidation.models.response.PlayerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Configuration
public class ValidateMultipleRequestService {

    private Validator validator;

    @Autowired
    public ValidateMultipleRequestService(Validator validator) {
        this.validator = validator;
    }

    public ResponseEntity<MultiplePlayersResponse> multiplePlayersResponse(MultiplePlayersRequest playerRequest) {
        List<PlayerResponse> playerResponse = new ArrayList<>();
        HttpStatus requestStatus = HttpStatus.OK;

        for (PlayerRequest player : playerRequest.getFirstNames()) {
            try {
                validateRequest(player);
                playerResponse.add(PlayerResponse.builder().firstName(player.getFirstName())
                        .team(player.getTeam())
                        .squad(player.getSquad()).build());
            } catch (CustomErrorDetailException e) {
                requestStatus = HttpStatus.MULTI_STATUS;
                playerResponse.add(PlayerResponse.builder().errorMessage(e.getMessage()).build());
            }
        }

        return ResponseEntity.status(requestStatus).body(MultiplePlayersResponse.builder()
                .teamPlayers(playerResponse)
                .build());
    }


    public void validateRequest(Object model) {
        Set<ConstraintViolation<Object>> validationErrors;
        validationErrors = validator.validate(model);
        if (!validationErrors.isEmpty()) {
            ConstraintViolation<Object> first = validationErrors.stream().findFirst().orElseThrow(() -> new CustomErrorDetailException(null, HttpStatus.BAD_REQUEST));
            throw new CustomErrorDetailException(first.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
