package exceptions;

import java.io.IOException;

public class AlreadyExistingDirectory extends IOException {
    public AlreadyExistingDirectory(String message) {
        super(message);
    }
}
