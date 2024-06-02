package pegas.service.exceptions;

public class RequestErrorException extends RuntimeException{
    public RequestErrorException(String message) {
        super(message);}
}
