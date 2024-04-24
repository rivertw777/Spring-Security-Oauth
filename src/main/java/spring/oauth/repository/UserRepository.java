package spring.oauth.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.oauth.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}