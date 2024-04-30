package com.dj.trip.global.exception;

import com.dj.trip.global.dto.ResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    // MethodArgumentNotValidException 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseDto<String> handleValidationException(MethodArgumentNotValidException ex) {
        log.error("----------------------------- 유효성 검사 에러 발생-----------------------------");
        ex.printStackTrace();
        return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), ex.getBindingResult().getFieldError().getDefaultMessage(), null);
    }
    
    // Exception 예외 처리
    @ExceptionHandler(Exception.class)
    protected ResponseDto<String> exceptionHandler(Exception ex) {
        log.error("-----------------------------에러 발생-----------------------------");
        ex.printStackTrace();
        return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), "에러 발생", null);
    }
}
