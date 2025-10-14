package com.vidaplus.sghss_api.exception;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class RestExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException ife && ife.getTargetType() != null && ife.getTargetType().isEnum()) {
            String campo = ife.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
            String valoresAceitos = Arrays.stream(ife.getTargetType().getEnumConstants()).map(Object::toString).collect(Collectors.joining(", "));
            String mensagem = String.format("O valor '%s' não é válido para o campo '%s'. Os valores aceitos são: [%s].", ife.getValue(), campo, valoresAceitos);
            
            Map<String, String> errorBody = Map.of("error", mensagem);
            return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
        }
        
        Map<String, String> errorBody = Map.of("error", "Formato da requisição JSON inválido.");
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class, IllegalArgumentException.class})
    public ResponseEntity<Map<String, String>> handleBusinessRuleException(RuntimeException ex) {
        logger.warn("Exceção de regra de negócio: {}", ex.getMessage());

        HttpStatus status = (ex instanceof IllegalArgumentException) ? HttpStatus.BAD_REQUEST : HttpStatus.CONFLICT;
        
        Map<String, String> errorBody = Map.of("error", ex.getMessage());
        return new ResponseEntity<>(errorBody, status);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleUnexpectedExceptions(Exception ex) {
        logger.error("Erro interno inesperado no servidor: {}", ex.getMessage(), ex);

        Map<String, String> errorBody = Map.of("error", "Ocorreu um erro inesperado no servidor. Tente novamente mais tarde.");
        return new ResponseEntity<>(errorBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}