package com.hboard.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Site_User create(String username, String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Site_User user = new Site_User(null, username, email, encodedPassword, UserRole.USER.getValue());
        this.userRepository.save(user);
        return user;
    }
}
