package pegas.controller;

import lombok.Data;

@Data
public class ResponsePaymentHandler {
    private final int statusCode;
    private final String message;
}
