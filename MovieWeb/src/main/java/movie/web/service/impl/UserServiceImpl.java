package movie.web.service.impl;

import lombok.RequiredArgsConstructor;
import movie.web.dto.RegistrationRequestDTO;
import movie.web.exception.EmailAlreadyExistsException;
import movie.web.exception.PasswordsMismatchException;
import movie.web.exception.UsernameAlreadyExistsException;
import movie.web.model.Role;
import movie.web.model.User;
import movie.web.repository.UserRepository;
import movie.web.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        userRepository.addUser(
                user.getEmail(),
                user.getUsername(),
                user.getPasswordHash(),
                user.getRole().name());
        return user;
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void deleteUserByID(Long id) {
        userRepository.deleteUserById(id);
    }

    @Override
    public void updateUser(Long id, User user) {
        if (getById(id) != null) {
            userRepository.updateUser(id,
                    user.getEmail(),
                    user.getUsername(),
                    user.getPasswordHash(),
                    user.getRole().name());
        }
    }

    @Override
    public User registerUser(RegistrationRequestDTO registrationRequestDTO) throws Exception {
        if(!registrationRequestDTO.getPassword().equals(registrationRequestDTO.getConfirmPassword())) {
            throw new PasswordsMismatchException( "Password doesn't match confirm password");
        }

        if (getByEmail(registrationRequestDTO.getEmail()) != null) {
            throw new EmailAlreadyExistsException( "This email is already in use");
        }

        if (getByUsername(registrationRequestDTO.getUsername()) != null) {
            throw new UsernameAlreadyExistsException("This username is already exists");
        }

        User user = new User();
        user.setEmail(registrationRequestDTO.getEmail());
        user.setUsername(registrationRequestDTO.getUsername());
        user.setPasswordHash(passwordEncoder.encode(registrationRequestDTO.getPassword()));
        user.setRole(Role.USER);

        return saveUser(user);
    }
}
