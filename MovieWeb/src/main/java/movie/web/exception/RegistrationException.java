package movie.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

public class RegistrationException extends Exception {
    public RegistrationException(String msg) {
        super(msg);
    }

}
