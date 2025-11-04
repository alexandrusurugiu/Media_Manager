package exception;

import java.io.IOException;

public class AlreadyExistingFile extends IOException {
    public AlreadyExistingFile(String message) {
        super(message);
    }
}
