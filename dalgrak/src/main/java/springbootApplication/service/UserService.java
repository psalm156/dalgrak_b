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

   
    public Long save(AddUserRequest dto) {
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword())) // 비밀번호 암호화
                .build()).getId();
    }

    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

   
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    
    public User createUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); // 비밀번호 암호화
        return userRepository.save(user);
    }

   
    public User updateUser(Long id, User updatedDetails) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (!existingUserOpt.isPresent()) {
            throw new RuntimeException("User not found with id: " + id);
        }

        User existingUser = existingUserOpt.get();
       
        existingUser.setUsername(updatedDetails.getUsername());  
        existingUser.setEmail(updatedDetails.getEmail());        
        existingUser.setPassword(bCryptPasswordEncoder.encode(updatedDetails.getPassword())); 
        
        return userRepository.save(existingUser);
    }

    
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}

