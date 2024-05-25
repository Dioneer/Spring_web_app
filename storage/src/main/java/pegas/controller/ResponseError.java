package pegas.controller;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseError{
        private final LocalDateTime time;
        private final String message;
}
