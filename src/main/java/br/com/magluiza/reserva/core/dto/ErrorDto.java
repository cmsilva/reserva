package br.com.magluiza.reserva.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ErrorDto implements Serializable {

    private static final long serialVersionUID = 1947256903791457367L;
    private String message;
    private String description;
    private List<FieldErrorDto> fieldErrors;

    public ErrorDto(String message, String description) {
        this.message = message;
        this.description = description;
        this.fieldErrors = new ArrayList<>();
    }

    public void add(String objectName, String field, String message) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }
        fieldErrors.add(new FieldErrorDto(objectName, field, message));
    }
}

