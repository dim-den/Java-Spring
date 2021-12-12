package movie.web.repository;

import movie.web.model.Role;
import movie.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true, value = "SELECT UserPackage.GetUsersCount FROM dual")
    Long getUsersCount();

    @Procedure("UserPackage.AddUser")
    void addUser(@Param("p_EMAIL") String email,
                  @Param("p_USERNAME") String username,
                  @Param("p_PASSWORD_HASH") String passwordHash,
                  @Param("p_ROLE") String role
    );

    @Procedure("UserPackage.UpdateUser")
    void updateUser(@Param("p_ID") Long id,
                    @Param("p_EMAIL") String email,
                    @Param("p_USERNAME") String username,
                    @Param("p_PASSWORD_HASH") String passwordHash,
                    @Param("p_ROLE") String role
    );

    @Procedure("UserPackage.DeleteUser")
    void deleteUserById(@Param("p_ID") Long id);

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
