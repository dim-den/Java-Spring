package movie.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import movie.web.aop.Loggable;
import movie.web.dto.AuthenticationRequestDTO;
import movie.web.dto.RegistrationRequestDTO;
import movie.web.exception.EmailAlreadyExistsException;
import movie.web.exception.PasswordsMismatchException;
import movie.web.exception.UsernameAlreadyExistsException;
import movie.web.model.User;
import movie.web.security.JwtTokenProvider;
import movie.web.service.UserService;
import movie.web.service.impl.UserServiceImpl;
import oracle.jdbc.OracleDatabaseException;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthRestController(AuthenticationManager authenticationManager,JwtTokenProvider jwtTokenProvider, UserServiceImpl userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Operation(summary = "Logging to system, returns JWT token if success")
    @Loggable
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            User user = userService.getByEmail(request.getEmail());
            if(user == null) new UsernameNotFoundException("User doesn't exists");

            String token = jwtTokenProvider.createToken(request.getEmail(), user.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("email", user.getEmail());
            response.put("token", token);
            response.put("role", user.getRole().toString());

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Wrong password or email", HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "Register user, returns JWT token if success")
    @Loggable
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequestDTO requestDTO) {
        try {
            userService.registerUser(requestDTO);

            String token = jwtTokenProvider.createToken(requestDTO.getEmail(), "USER");
            Map<Object, Object> response = new HashMap<>();

            response.put("email", requestDTO.getEmail());
            response.put("token", token);
            response.put("role", "USER");

            return ResponseEntity.ok(response);
        } catch (JpaSystemException e) {
            GenericJDBCException cause = (GenericJDBCException) e.getCause();
            int errorCode = cause.getErrorCode();
            String errorMessage = "An error occurred";
            switch (errorCode) {
                case 20111: errorMessage = "Email already exists"; break;
                case 20112: errorMessage = "Username already exists"; break;
                case 20113: errorMessage = "Wrong role name"; break;
            }

            return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
        }
        catch (PasswordsMismatchException e) {
            return new ResponseEntity<>("Password doesn't match confirm password", HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "Logout from system")
    @Loggable
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
