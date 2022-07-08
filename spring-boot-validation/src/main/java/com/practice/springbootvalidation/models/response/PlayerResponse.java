package com.practice.springbootvalidation.models.response;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerResponse {
    private String firstName;
    private String lastName;
    private String team;
}
