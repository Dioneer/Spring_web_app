package pegas.controller;

import lombok.Data;

@Data
public class ResponseError{
        private final int statusCode;
        private final String message;
}
