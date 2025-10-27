package exception;

import java.io.IOException;

public class FilesSavingException extends IOException {
    public FilesSavingException(String message) {
        super(message);
    }
}
