package edu.escuelaing.ieti.tenistourcol.model;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class ExceptionResponse extends Response{

    public ExceptionResponse(Date timestamp, Integer status, String message, String error) {
        super(timestamp, status, message);
        this.error = error;
    }

    private String error;

    @Override
    public String toString() {
        return "ExceptionResponse{" +
                "timestamp='" + this.getTimestamp() + '\'' +
                ", status='" + this.getStatus() + '\'' +
                ", message='" + this.getMessage() + '\'' +
                ", error='" + error + '\'' +
                "} ";
    }
}
