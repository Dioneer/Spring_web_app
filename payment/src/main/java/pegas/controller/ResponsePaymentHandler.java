package pegas.controller;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponsePaymentHandler {
    private final LocalDateTime time;
    private final String message;
}
