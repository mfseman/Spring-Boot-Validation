package com.practice.springbootvalidation.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomErrorDetailException extends RuntimeException {
    private String message;
    private HttpStatus statusCode;
}
