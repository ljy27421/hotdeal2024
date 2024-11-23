package com.hotdealwork.hotdealwork.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SiteUser, Long> {

    Optional<SiteUser> findByusername(String username);

    Optional<SiteUser> findByEmail(String email);  // 이메일로 사용자 검색 추가

    @Transactional
    void deleteByUsername(String username);

    boolean existsByUsername(String username);
}