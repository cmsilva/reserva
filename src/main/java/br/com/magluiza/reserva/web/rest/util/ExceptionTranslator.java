package br.com.magluiza.reserva.web.rest.util;

import br.com.magluiza.reserva.core.MessageConstants;
import br.com.magluiza.reserva.core.dto.ErrorDto;
import br.com.magluiza.reserva.core.dto.ParameterizedErrorDto;
import br.com.magluiza.reserva.core.exception.CustomParameterizedException;
import br.com.magluiza.reserva.core.exception.NegocioException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807)
 */
@ControllerAdvice
public class ExceptionTranslator {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDto processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        ErrorDto dto = new ErrorDto(MessageConstants.ERR_VALIDATION, "");
        for (FieldError fieldError : fieldErrors) {
            dto.add(fieldError.getObjectName(), fieldError.getField(), fieldError.getCode());
        }
        return dto;
    }

    @ExceptionHandler(CustomParameterizedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ParameterizedErrorDto processParameterizedValidationError(CustomParameterizedException ex) {
        return ex.getErrorDto();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorDto processMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return new ErrorDto(MessageConstants.ERR_METHOD_NOT_SUPPORTED, exception.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto processHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception) {
        return new ErrorDto(MessageConstants.ERR_MEDIA_NOT_SUPPORTED, exception.getMessage());
    }

    @ExceptionHandler(NegocioException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto processNegocioException(NegocioException exception) {
        return new ErrorDto(exception.getChave(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> processRuntimeException(Exception ex) {
        BodyBuilder builder;
        ErrorDto errorDto;
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            builder = ResponseEntity.status(responseStatus.value());
            errorDto = new ErrorDto("error." + responseStatus.value().value(), responseStatus.reason());
        } else {
            builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
            errorDto = new ErrorDto(MessageConstants.ERR_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
        return builder.body(errorDto);
    }
}
