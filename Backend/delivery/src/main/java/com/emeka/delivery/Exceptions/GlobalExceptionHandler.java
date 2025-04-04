package com.emeka.delivery.Exceptions;

import java.util.Date;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;




@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorObject> handlerNotFoundException(NotFoundException ex){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorObject> handlerConflictException(ConflictException ex){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.CONFLICT.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ErrorObject> handlerAuthenticationCredentialsNotFoundException(JwtAuthenticationException ex){

        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorObject> handlerInternalServer(Exception ex){

        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
 @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body("Tu sesión ha caducado. Por favor, vuelve a iniciar sesión.");
    }


}
