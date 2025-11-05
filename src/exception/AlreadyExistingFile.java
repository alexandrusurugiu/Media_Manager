package exception;

import java.io.IOException;

public class AlreadyExistingFile extends RuntimeException {
    public AlreadyExistingFile(String message) {
        super(message);
    }
}
