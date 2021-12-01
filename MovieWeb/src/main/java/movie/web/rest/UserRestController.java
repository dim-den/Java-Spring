package movie.web.rest;

import movie.web.aop.Loggable;
import movie.web.dto.UserDTO;
import movie.web.model.Film;
import movie.web.model.User;
import movie.web.service.UserService;
import movie.web.service.impl.UserServiceImpl;
import movie.web.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RequestMapping("/api")
@RestController
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Loggable
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(Mapper.mapAll(userService.getAllUsers(), UserDTO.class));
    }

    @Loggable
    @PostMapping("/user/save")
    public void saveUser(@Valid @RequestBody UserDTO userDTO) {
        userService.saveUser(Mapper.map(userDTO, User.class));
    }

    @Loggable
    @GetMapping("/user/{id}")
    ResponseEntity<UserDTO> getUserById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(Mapper.map(userService.getById(id), UserDTO.class));
    }

    @Loggable
    @PutMapping("/user/update/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {
        userService.updateUser(id, Mapper.map(userDTO, User.class));
    }

    @Loggable
    @DeleteMapping("/user/delete/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void deleteUser(@PathVariable @Positive Long id) {
        userService.deleteUserByID(id);
    }

}
