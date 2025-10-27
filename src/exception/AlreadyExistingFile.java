package exception;

public class AlreadyExistingFile extends RuntimeException {
    public AlreadyExistingFile(String message) {
        super(message);
    }
}
