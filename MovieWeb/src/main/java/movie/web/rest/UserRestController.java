package movie.web.rest;

import lombok.RequiredArgsConstructor;
import movie.web.model.User;
import movie.web.service.UserService;
import movie.web.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/users/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @GetMapping("/users/{id}")
    ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getByID(id));
    }

}
