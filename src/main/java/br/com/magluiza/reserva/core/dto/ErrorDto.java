package br.com.magluiza.reserva.core.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ErrorDto implements Serializable {

    private static final long serialVersionUID = 1947256903791457367L;
    private final String message;
    private final String description;
    private List<FieldErrorDto> fieldErrors;

    public ErrorDto(String message) {
        this(message, "");
    }

    public ErrorDto(String message, String description) {
        this.message = message;
        this.description = description;
        this.fieldErrors = new ArrayList<>();
    }

    public List<FieldErrorDto> getFieldErrors() {
        return fieldErrors;
    }

    public void add(String objectName, String field, String message) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }
        fieldErrors.add(new FieldErrorDto(objectName, field, message));
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}

