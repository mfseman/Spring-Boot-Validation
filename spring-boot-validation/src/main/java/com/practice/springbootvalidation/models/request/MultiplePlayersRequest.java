package com.practice.springbootvalidation.models.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MultiplePlayersRequest {

    @NotNull(message = "First names list is required")
    @NotEmpty(message = "First names are required")
    @Size(max = 5, message = "You can only provide less than 5 names")
    private List<PlayerRequest> firstNames;
}
