package com.example.practiceproject.starter.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "note.audit")
public class NoteAuditProperties {

    private String prefix = "[NOTE-AUDIT]";

    private boolean enabled = true;

}
