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
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "issue_events")
public abstract class IssueEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid")
    protected UUID uuid;
    
    @Column(name = "created_at")
    protected LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    protected IssueEventType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_uuid")
    protected Issue issue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_uuid")
    protected User author;
}
