package springbootApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootApplication.domain.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
 //   Optional<User> findByEmail(String email);
    boolean existsByEmail(String email); // 이메일 중복 체크
    boolean existsById(Long userId); 
}