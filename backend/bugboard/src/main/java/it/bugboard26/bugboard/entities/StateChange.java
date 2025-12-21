package it.bugboard26.bugboard.entities;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import it.bugboard26.bugboard.enums.ChangeType;
import it.bugboard26.bugboard.enums.IssueState;
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
@Table(name = "state_changes")
@Entity
public class StateChange extends Change {
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "old")
    private IssueState oldState;
    
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "new")
    private IssueState newState;

    public StateChange() {
        this.changeType = ChangeType.STATE;
    }

    @Override
    public Issue apply(Issue issue) {
        issue.setState(this.newState);
        return issue;
    }
}
