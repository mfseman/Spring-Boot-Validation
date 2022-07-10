package com.practice.springbootvalidation.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String team;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String squad;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorMessage;
}
