package edu.escuelaing.ieti.tenistourcol.controller;

import edu.escuelaing.ieti.tenistourcol.exception.NotFoundException;
import edu.escuelaing.ieti.tenistourcol.model.ExceptionResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.text.MessageFormat;
import java.util.Date;

@ControllerAdvice
public class Handler {

    protected static Logger logger = LogManager.getLogger(Handler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {

        BindingResult result = ex.getBindingResult();
        StringBuffer lsbErrors = new StringBuffer();

        result.getFieldErrors().stream().forEach( err -> lsbErrors.append( MessageFormat.format("El campo {0} {1}.", err.getField(), err.getDefaultMessage() ) ) );

        String mensaje = lsbErrors.toString();

        return new ExceptionResponse(new Date(), 400, mensaje, "Bad Request");
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ExceptionResponse handleNotFoundException(NotFoundException ex, WebRequest request) {

        return new ExceptionResponse(new Date(), 404, ex.getMessage(), "Not Found");
    }

}
