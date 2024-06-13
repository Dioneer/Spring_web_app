package pegas.service.exceptions;

/**
 * realisation in adviceController
 */
public class RequestErrorException extends RuntimeException {
        public RequestErrorException(String message) {
            super(message);
    }
}
