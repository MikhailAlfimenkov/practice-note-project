package com.example.practiceproject.starter.service;


import com.example.practiceproject.starter.config.NoteAuditProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AuditLoggerService {

    private final NoteAuditProperties properties;

    public void logAction(String action, String details) {
        log.info("[{}] Action: {} | Details: {}", properties.getPrefix(), action, details);
    }

}
