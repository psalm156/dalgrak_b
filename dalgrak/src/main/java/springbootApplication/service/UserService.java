package springbootApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootApplication.repository.UserRepository;
import springbootApplication.domain.User;
import springbootApplication.dto.AddUserRequest;
import springbootApplication.exception.EmailAlreadyInUseException;
import springbootApplication.exception.UserIdAlreadyInUseException;
import springbootApplication.exception.UserNotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // 사용자 저장 (회원가입)
    @Transactional
    public Long save(AddUserRequest dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyInUseException("Email already in use");
        }

        // 사용자 아이디 중복 확인
        if (userRepository.existsById(dto.getUserId())) {
            throw new UserIdAlreadyInUseException("UserId already in use");
        }

        User user = User.builder()
                .userId(dto.getUserId())
                .email(dto.getEmail())
                .username(dto.getName())
                .password(bCryptPasswordEncoder.encode(dto.getPassword())) // 비밀번호 암호화
                .build();
        
        return userRepository.save(user).getUserId();
    }

    // 사용자 생성
    @Transactional
    public User createUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); // 비밀번호 암호화
        return userRepository.save(user);
    }

    // 사용자 업데이트
    @Transactional
    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(userDetails.getUsername());
                    user.setEmail(userDetails.getEmail());
                    user.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword())); // 비밀번호 암호화
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    // 사용자 삭제
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
