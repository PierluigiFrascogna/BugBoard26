package it.bugboard26.bugboard.entities;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import it.bugboard26.bugboard.enums.ChangeType;
import it.bugboard26.bugboard.enums.Priority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@PrimaryKeyJoinColumn(name = "uuid")
@Table(name = "priority_changes")
@Entity
public class PriorityChange extends Change {
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "old")
    private Priority oldPriority;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "new")
    private Priority newPriority;

    public PriorityChange() {
        this.changeType = ChangeType.PRIORITY;
    }

    @Override
    public Issue apply(Issue issue) {
        issue.setPriority(this.newPriority);
        return issue;
    }
}
