package it.bugboard26.bugboard.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import it.bugboard26.bugboard.enums.IssueEventType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "issue_events")
public abstract class IssueEvent {
    @Id
    @Column(name = "uuid")
    private UUID uuid;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private IssueEventType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_uuid")
    private Issue issue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_uuid")
    private User author;
}
