package edu.escuelaing.ieti.TenisTourCol.controller;

import edu.escuelaing.ieti.TenisTourCol.model.ExceptionResponse;
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
public class HandlerException {

        protected static Logger logger = LogManager.getLogger(HandlerException.class);

        @ExceptionHandler(MethodArgumentNotValidException.class)
        @ResponseStatus(code = HttpStatus.BAD_REQUEST)
        @ResponseBody
        public ExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {

            BindingResult result = ex.getBindingResult();
            StringBuffer lsb_errors = new StringBuffer();

            result.getFieldErrors().stream().forEach( err -> lsb_errors.append( MessageFormat.format("El campo {0} {1}.", err.getField(), err.getDefaultMessage() ) ) );

            String mensaje = lsb_errors.toString();

            if (logger.isDebugEnabled())
                logger.error( mensaje );

            return new ExceptionResponse(new Date(), 400, "Bad Request", mensaje);
        }

    }
