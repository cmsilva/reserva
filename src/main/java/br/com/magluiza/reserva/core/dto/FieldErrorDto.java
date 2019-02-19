package br.com.magluiza.reserva.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class FieldErrorDto implements Serializable {

    private static final long serialVersionUID = -717570199245967685L;
    private String objectName;
    private String field;
    private String message;

}
