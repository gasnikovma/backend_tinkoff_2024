package edu.java.bot.models.response;

import java.util.List;

public record ApiErrorResponse(
    String descripton,
    String code,
    String exceptionName,
    String exceptionMessage,
    List<String> stacktrace

) {
}
