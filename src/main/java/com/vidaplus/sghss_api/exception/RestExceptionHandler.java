package com.vidaplus.sghss_api.exception;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
    
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleDataIntegrityViolation(RuntimeException ex) {
        return Map.of("error", ex.getMessage());
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) cause;
            
            if (ife.getTargetType() != null && ife.getTargetType().isEnum()) {
                
                String campo = ife.getPath().stream()
                                  .map(ref -> ref.getFieldName())
                                  .collect(Collectors.joining("."));

                String valoresAceitos = Arrays.stream(ife.getTargetType().getEnumConstants())
                                              .map(Object::toString)
                                              .collect(Collectors.joining(", "));

                String mensagem = String.format("O valor '%s' não é válido para o campo '%s'. Os valores aceitos são: [%s].", 
                                                ife.getValue(), campo, valoresAceitos);

                Map<String, String> errorBody = Map.of("error", mensagem);
                return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
            }
        }
        
        Map<String, String> errorBody = Map.of("error", "Formato da requisição JSON inválido.");
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }
}