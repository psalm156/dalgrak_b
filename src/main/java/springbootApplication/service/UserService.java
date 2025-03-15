package springbootApplication.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springbootApplication.repository.UserRepository;
import springbootApplication.domain.User;
import springbootApplication.dto.AddUserRequest;

import java.util.List;
import java.util.Optional;

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
    public Long save(AddUserRequest dto) {
    	 if (userRepository.existsByEmail(dto.getEmail())) {
             throw new RuntimeException("Email already in use"); // 이메일 중복 예외 처리
         }

         // 사용자 아이디 중복 확인 (userId가 중복되면 안됨)
         if (userRepository.existsById(dto.getUserId())) {
             throw new RuntimeException("UserId already in use"); // userId 중복 예외 처리
         }
         
        return userRepository.save(User.builder()
        		.userId(dto.getUserId()) // userId가 중복되지 않도록 확인 후 사용
                .email(dto.getEmail())
                .username(dto.getName())
                .password(bCryptPasswordEncoder.encode(dto.getPassword())) // 비밀번호 암호화
                .build()).getUserId();
    }


    // 사용자 생성
    public User createUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); // 비밀번호 암호화
        return userRepository.save(user);
    }

    // 사용자 업데이트
    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(userDetails.getUsername());
                    user.setEmail(userDetails.getEmail());
                    user.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword())); // 비밀번호 암호화
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // 사용자 삭제
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}

