package com.practice.springbootvalidation.models.response;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MultiplePlayersResponse {
    private List<PlayerResponse> teamPlayers;
}
