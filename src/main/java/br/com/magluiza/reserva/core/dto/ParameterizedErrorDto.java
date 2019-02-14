package br.com.magluiza.reserva.core.dto;


import java.io.Serializable;
import java.util.Map;

public class ParameterizedErrorDto implements Serializable {

    private static final long serialVersionUID = -5663028837566636731L;
    private final String message;
    private final Map<String, String> paramMap;

    public ParameterizedErrorDto(String message, Map<String, String> paramMap) {
        this.message = message;
        this.paramMap = paramMap;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }
}
