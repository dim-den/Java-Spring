package movie.web.service;

import movie.web.model.Film;
import movie.web.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User saveUser(User user);
    User getById(Long id);
    User getByEmail(String email);
    void deleteUserByID(Long id);
    void updateUser(Long id, User user);
}
