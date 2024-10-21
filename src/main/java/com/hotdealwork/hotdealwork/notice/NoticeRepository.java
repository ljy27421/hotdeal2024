package com.hotdealwork.hotdealwork.notice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    // 필요한 추가 쿼리 메서드 작성 가능
}