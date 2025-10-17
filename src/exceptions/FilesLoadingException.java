package exceptions;

import java.io.IOException;

public class FilesLoadingException extends IOException {
    public FilesLoadingException(String message) {
        super(message);
    }
}
