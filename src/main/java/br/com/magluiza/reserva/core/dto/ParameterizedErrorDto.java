package br.com.magluiza.reserva.core.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
public class ParameterizedErrorDto implements Serializable {

    private static final long serialVersionUID = -5663028837566636731L;
    private String message;
    private Map<String, String> paramMap;

}
