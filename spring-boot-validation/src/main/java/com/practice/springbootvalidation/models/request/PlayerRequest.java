package com.practice.springbootvalidation.models.request;

import com.practice.springbootvalidation.validation.CustomFieldValidation;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CustomFieldValidation(hasOnlyOne = {"team", "squad"}, message = "You must choose either: 'team' or 'squad'")
public class PlayerRequest {

    @NotBlank(message = "Your first name cannot be empty")
    @Pattern(regexp = "\\b([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+", message = "Your first name is invalid")
    @Size(min = 1, max = 20, message = "Your first name is too large")
    private String firstName;

    private String team;

    private String squad;
}
