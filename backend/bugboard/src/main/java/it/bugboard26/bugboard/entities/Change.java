package it.bugboard26.bugboard.entities;


import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import it.bugboard26.bugboard.enums.ChangeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "uuid")
@Table(name = "changes")
@Entity
public abstract class Change extends IssueEvent {
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "type")
    private ChangeType changeType;
}
