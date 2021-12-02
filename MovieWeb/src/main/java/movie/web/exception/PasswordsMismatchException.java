package movie.web.exception;

public class PasswordsMismatchException extends Exception {
    public PasswordsMismatchException(String msg) {
        super(msg);
    }
}
