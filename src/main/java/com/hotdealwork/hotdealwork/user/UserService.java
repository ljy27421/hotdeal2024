package com.hotdealwork.hotdealwork.user;

import com.hotdealwork.hotdealwork.DataNotFoundException;
import com.hotdealwork.hotdealwork.board.Board;
import com.hotdealwork.hotdealwork.board.BoardRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
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

    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
        if (siteUser.isPresent()){
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }

//    @Transactional
//    public void addInterest(Long userId, Integer boardId) {
//        SiteUser siteUser = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
//        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("Board not found"));

//        List<Double> boardVector = board.getEmbeddingVector();
//        List<Double> userVector = siteUser.getInterestVector();
//
//        if (userVector == null || userVector.isEmpty()) {
//            userVector = new ArrayList<>(boardVector);
//        } else {
//            for (int i = 0; i < boardVector.size(); i++) {
//                userVector.set(i, (userVector.get(i) + boardVector.get(i)) / 2.0);
//            }
//        }
//
//        siteUser.setInterestVector(userVector);
//        userRepository.save(siteUser);
//    }
}
