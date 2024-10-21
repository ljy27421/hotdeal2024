package com.hotdealwork.hotdealwork.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    public void createNotice(String title, String content) {
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContent(content);
        noticeRepository.save(notice);
    }

    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }
}