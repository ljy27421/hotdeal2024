package com.hotdealwork.hotdealwork.user;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpServletRequest request;

    public SiteUser create(String username, String email, String password){
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }

    public boolean deleteUser(String username, String password) {
        Optional<SiteUser> userOptional = userRepository.findByusername(username);

        if (userOptional.isPresent()) {
            SiteUser user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                userRepository.deleteByUsername(username);
                SecurityContextHolder.clearContext();
                request.getSession().invalidate();
                return true;
            }
        }

        return false;
    }
}
