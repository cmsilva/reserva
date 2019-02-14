package br.com.magluiza.reserva.core.dto;

import java.io.Serializable;

public class FieldErrorDto implements Serializable {

    private static final long serialVersionUID = -717570199245967685L;
    private final String objectName;
    private final String field;
    private final String message;

    public FieldErrorDto(String dto, String field, String message) {
        this.objectName = dto;
        this.field = field;
        this.message = message;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}