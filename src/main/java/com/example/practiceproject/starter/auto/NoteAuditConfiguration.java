package com.example.practiceproject.starter.auto;

import com.example.practiceproject.starter.config.NoteAuditProperties;
import com.example.practiceproject.starter.service.AuditLoggerService;
import lombok.Data;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@Data
@AutoConfiguration
@EnableConfigurationProperties(NoteAuditProperties.class)
@ConditionalOnProperty(
        prefix = "note.audit",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class NoteAuditConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AuditLoggerService createAuditLoggerService(NoteAuditProperties properties) {
        return new AuditLoggerService(properties);
    }
}
