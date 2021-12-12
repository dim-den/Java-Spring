package movie.web.service;

import movie.web.dto.RegistrationRequestDTO;
import movie.web.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    Page<User> getUsersPaginated(int page, int size);
    Long getUsersCount();
    User saveUser(User user);
    User getById(Long id);
    User getByEmail(String email);
    User getByUsername(String username);
    void deleteUserByID(Long id);
    void updateUser(Long id, User user);
    User registerUser(RegistrationRequestDTO registrationRequestDTO) throws Exception;
}
