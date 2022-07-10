package com.practice.springbootvalidation.models.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MultiplePlayersRequest {
    private List<PlayerRequest> firstNames;
}
