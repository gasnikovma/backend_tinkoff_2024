package edu.java.models.dto.response;

import java.util.List;

public record ApiErrorResponse(
    String descripton,
    String code,
    String exceptionName,
    String exceptionMessage,
    List<String> stacktrace

) {
}
