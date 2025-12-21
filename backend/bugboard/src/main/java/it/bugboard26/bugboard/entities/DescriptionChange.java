package it.bugboard26.bugboard.entities;

import it.bugboard26.bugboard.enums.ChangeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@PrimaryKeyJoinColumn(name = "uuid")
@Table(name = "description_changes")
@Entity
public class DescriptionChange extends Change {
    @Column(name = "old")
    private String oldDescription;

    @Column(name = "new")
    private String newDescription;

    public DescriptionChange() {
        this.changeType = ChangeType.DESCRIPTION;
    }

    @Override
    public Issue apply(Issue issue) {
        issue.setDescription(this.newDescription);
        return issue;
    }
    
}
