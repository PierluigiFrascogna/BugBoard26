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
@Table(name = "title_changes")
@Entity
public class TitleChange extends Change {
    @Column(name = "old")
    private String oldTitle;
    
    @Column(name = "new")
    private String newTitle;

    public TitleChange() {
        this.changeType = ChangeType.TITLE;
    }

    @Override
    public Issue apply(Issue issue) {
        issue.setTitle(this.newTitle);
        return issue;
    }

}
