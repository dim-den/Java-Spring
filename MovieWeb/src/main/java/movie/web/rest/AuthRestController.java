package movie.web.rest;

import movie.web.aop.Loggable;
import movie.web.dto.AuthenticationRequestDTO;
import movie.web.dto.RegistrationRequestDTO;
import movie.web.model.User;
import movie.web.repository.UserRepository;
import movie.web.security.JwtTokenProvider;
import movie.web.service.UserService;
import movie.web.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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

    @Loggable
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequestDTO requestDTO) {
        try {
            User user = userService.registerUser(requestDTO);

            String token = jwtTokenProvider.createToken(user.getEmail(), user.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("email", user.getEmail());
            response.put("token", token);
            response.put("role", user.getRole().toString());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @Loggable
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
