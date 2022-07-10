package com.practice.springbootvalidation.service;

import com.practice.springbootvalidation.models.request.MultiplePlayersRequest;
import com.practice.springbootvalidation.models.request.PlayerRequest;
import com.practice.springbootvalidation.models.response.MultiplePlayersResponse;
import com.practice.springbootvalidation.models.response.PlayerResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class ValidateMultipleRequestService {

    public MultiplePlayersResponse multiplePlayersResponse(MultiplePlayersRequest playerRequest) {
        List<PlayerResponse> playerResponse = new ArrayList<>();

        for (PlayerRequest player : playerRequest.getFirstNames()) {
            playerResponse.add(PlayerResponse.builder().firstName(player.getFirstName()).build());
        }

        return MultiplePlayersResponse.builder()
                .teamPlayers(playerResponse)
                .build();
    }
}
