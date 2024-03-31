package com.hboard.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        SiteUser user = new SiteUser(null, username, encodedPassword, email, UserRole.USER.getValue());
        this.userRepository.save(user);
        return user;
    }

    public SiteUser getUser(String username) {
        SiteUser siteUser = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("siteuser not found"));
        return siteUser;
    }
}
