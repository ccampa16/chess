package dataAccess.Exceptions;

public class BadRequestException extends DataAccessException {
    public BadRequestException(String message){
        super(message);
    }
}
