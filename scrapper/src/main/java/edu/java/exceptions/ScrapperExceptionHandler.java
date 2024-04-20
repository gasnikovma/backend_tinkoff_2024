package edu.java.exceptions;

import edu.java.models.dto.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ScrapperExceptionHandler {

    @ExceptionHandler(ChatAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleRegistrationChat(ChatAlreadyExistsException exception) {
        return new ApiErrorResponse(
            "Wrong",
            "400",
            ChatAlreadyExistsException.class.getName(),
            exception.getMessage(),
            null);


    }

}
