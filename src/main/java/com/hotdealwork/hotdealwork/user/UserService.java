package com.hotdealwork.hotdealwork.user;

import com.hotdealwork.hotdealwork.DataNotFoundException;
import com.hotdealwork.hotdealwork.board.BoardRepository;
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
    private final BoardRepository boardRepository;
    private final PasswordEncoder passwordEncoder;

    // 사용자 인증 메서드 (아이디와 비밀번호 확인)
    public boolean authenticateUser(String username, String password) {
        Optional<SiteUser> optionalUser = userRepository.findByusername(username);
        if (optionalUser.isPresent()) {
            SiteUser user = optionalUser.get();
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

    // 특정 유저 조회 메서드
    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("사용자를 찾을 수 없습니다.");
        }
    }

    // 비밀번호 찾기 로직 (질문과 답변 확인)
    public String findPassword(String username, String selectedQuestion, String securityAnswer) throws Exception {
        Optional<SiteUser> optionalUser = userRepository.findByusername(username);

        if (optionalUser.isPresent()) {
            SiteUser user = optionalUser.get();

            if (user.getSelectedQuestion().equals(selectedQuestion) &&
                    user.getSecurityAnswer().equals(securityAnswer)) {
                return "******";  // 실제 비밀번호 대신 힌트를 제공하거나 비밀번호 재설정 로직으로 변경 가능
            } else {
                throw new Exception("질문 또는 답변이 일치하지 않습니다.");
            }
        } else {
            throw new Exception("사용자를 찾을 수 없습니다.");
        }
    }

    // 보안 질문 답변 확인 로직
    public boolean verifySecurityAnswer(String username, String selectedQuestion, String securityAnswer) throws Exception {
        Optional<SiteUser> optionalUser = userRepository.findByusername(username);

        if (optionalUser.isPresent()) {
            SiteUser user = optionalUser.get();

            if (user.getSelectedQuestion().equals(selectedQuestion) &&
                    user.getSecurityAnswer().equals(securityAnswer)) {
                return true;  // 질문과 답변이 일치하면 true 반환
            } else {
                throw new Exception("질문 또는 답변이 일치하지 않습니다.");
            }
        } else {
            throw new Exception("사용자를 찾을 수 없습니다.");
        }
    }

    // 비밀번호 재설정 메서드
    public void updatePassword(String username, String newPassword) throws Exception {
        Optional<SiteUser> optionalUser = userRepository.findByusername(username);
        if (optionalUser.isPresent()) {
            SiteUser user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new Exception("사용자를 찾을 수 없습니다.");
        }
    }

    // 이메일로 유저 아이디 찾기 메서드
    public String findUsernameByEmail(String email) throws Exception {
        Optional<SiteUser> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get().getUsername();
        } else {
            throw new Exception("사용자를 찾을 수 없습니다.");
        }
    }

    // 기존 회원 생성 메서드
    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }

    // 닉네임 및 비밀번호 찾기 질문을 추가한 회원 생성 메서드
    public SiteUser create(String username, String email, String password,
                           String nickname, String selectedQuestion, String securityAnswer) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setNickname(nickname);  // 닉네임 추가
        user.setSelectedQuestion(selectedQuestion);  // 선택한 비밀번호 찾기 질문 추가
        user.setSecurityAnswer(securityAnswer);  // 질문에 대한 답변 추가
        this.userRepository.save(user);
        return user;
    }

    // 회원 탈퇴 메서드
    public boolean deleteUser(String username, String password) {
        Optional<SiteUser> userOptional = userRepository.findByusername(username);

        if (userOptional.isPresent()) {
            SiteUser user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                userRepository.deleteByUsername(username);
                SecurityContextHolder.clearContext();
                return true;
            }
        }

        return false;
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

