package movie.web.service;

import movie.web.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User saveUser(User user);
    User getByID(Long id);
    User getByEmail(String email);
}
