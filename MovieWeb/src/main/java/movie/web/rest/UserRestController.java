package movie.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import movie.web.aop.Loggable;
import movie.web.dto.GenreDTO;
import movie.web.dto.UserDTO;
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

    @Operation(summary = "Get all users", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(Mapper.mapAll(userService.getAllUsers(), UserDTO.class));
    }

    @Operation(summary = "Get page of users", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping(value = "/users", params = { "page", "size" })
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<UserDTO>> getUsersPaginated(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(Mapper.mapAll(userService.getUsersPaginated(page, size).toList(), UserDTO.class));
    }

    @Operation(summary = "Get count of users", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping("/users/count")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<Long> getUsersCount() {
        return ResponseEntity.ok(userService.getUsersCount());
    }

    @Operation(summary = "Save user", security = @SecurityRequirement(name = "developers:write"))
    @Loggable
    @PostMapping("/user/save")
    public void saveUser(@Valid @RequestBody UserDTO userDTO) {
        userService.saveUser(Mapper.map(userDTO, User.class));
    }

    @Operation(summary = "Get user by ID", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping("/user/{id}")
    ResponseEntity<UserDTO> getUserById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(Mapper.map(userService.getById(id), UserDTO.class));
    }

    @Operation(summary = "Update user by ID", security = @SecurityRequirement(name = "developers:write"))
    @Loggable
    @PutMapping("/user/update/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {
        userService.updateUser(id, Mapper.map(userDTO, User.class));
    }

    @Operation(summary = "Delete user by ID", security = @SecurityRequirement(name = "developers:write"))
    @Loggable
    @DeleteMapping("/user/delete/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void deleteUser(@PathVariable @Positive Long id) {
        userService.deleteUserByID(id);
    }

}
