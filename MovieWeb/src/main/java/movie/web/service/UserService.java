package movie.web.service;

import movie.web.dto.AuthenticationRequestDTO;
import movie.web.dto.RegistrationRequestDTO;
import movie.web.exception.PasswordsMismatchException;
import movie.web.model.User;
import oracle.jdbc.OracleDatabaseException;
import org.springframework.data.domain.Page;
import org.springframework.orm.jpa.JpaSystemException;

import java.sql.SQLException;
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
    void login(AuthenticationRequestDTO authenticationRequestDTO) throws JpaSystemException;
    void registerUser(RegistrationRequestDTO registrationRequestDTO) throws JpaSystemException, PasswordsMismatchException;
}
