package exception;

import java.io.IOException;

public class StatisticsSavingException extends IOException {
    public StatisticsSavingException(String message) {
        super(message);
    }
}
