package movie.web.service.impl;

import lombok.RequiredArgsConstructor;
import movie.web.dto.AuthenticationRequestDTO;
import movie.web.dto.RegistrationRequestDTO;
import movie.web.exception.PasswordsMismatchException;
import movie.web.model.User;
import movie.web.repository.UserRepository;
import movie.web.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.jpa.JpaSystemException;
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
    public Page<User> getUsersPaginated(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Long getUsersCount() {
        return userRepository.getUsersCount();
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
    public void login(AuthenticationRequestDTO authenticationRequestDTO) throws JpaSystemException {
        String encoded = passwordEncoder.encode(authenticationRequestDTO.getPassword());
        userRepository.login(authenticationRequestDTO.getEmail(), encoded);
    }

    @Override
    public void registerUser(RegistrationRequestDTO registrationRequestDTO) throws JpaSystemException, PasswordsMismatchException {
        if (!registrationRequestDTO.getPassword().equals(registrationRequestDTO.getConfirmPassword())) {
            throw new PasswordsMismatchException("Password doesn't match confirm password");
        }

        userRepository.register(registrationRequestDTO.getEmail(),
                registrationRequestDTO.getUsername(),
                passwordEncoder.encode(registrationRequestDTO.getPassword()),
                "USER");
    }
}
