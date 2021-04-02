package edu.escuelaing.ieti.tenistourcol.model;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SuccessResponse extends Response{

    public SuccessResponse(Date timestamp, Integer status, String message, String body) {
        super(timestamp, status, message);
        this.body = body;
    }

    private String body;

    @Override
    public String toString() {
        return "SuccessResponse{" +
                "timestamp='" + this.getTimestamp() + '\'' +
                ", status='" + this.getStatus() + '\'' +
                ", message='" + this.getMessage() + '\'' +
                ", body='" + body + '\'' +
                "} ";
    }
}
