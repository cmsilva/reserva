package br.com.magluiza.reserva.web.rest.errors;

import br.com.magluiza.reserva.web.rest.dto.ParameterizedErrorDto;

import java.util.HashMap;
import java.util.Map;

/**
 * Exce&ccedil;&atilde;o parametrizavel, que pode ser traduzida do lado do cliente.
 * Por examplo:
 *
 * <pre>
 * throw new CustomParameterizedException(&quot;argumentosInvalidos&quot;, &quot;descricao&quot;, &quot;titulo&quot;);
 * </pre>
 * <p>
 * Pode ser traduzida como:
 *
 * <pre>
 * "error.argumentosInvalidos" :  "Argumentos {{param0}} e {{param1}}"
 * </pre>
 */
public class CustomParameterizedException extends RuntimeException {
    private static final long serialVersionUID = -3028517966200827010L;

    private static final String PARAM = "param";
    private final String message;
    private final Map<String, String> paramMap = new HashMap<>();

    public CustomParameterizedException(String message, String... params) {
        super(message);
        this.message = message;
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                paramMap.put(PARAM + i, params[i]);
            }
        }
    }

    public CustomParameterizedException(String message, Map<String, String> paramMap) {
        super(message);
        this.message = message;
        this.paramMap.putAll(paramMap);
    }

    public ParameterizedErrorDto getErrorDto() {
        return new ParameterizedErrorDto(message, paramMap);
    }
}
