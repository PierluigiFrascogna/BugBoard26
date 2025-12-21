package it.bugboard26.bugboard.modules.issue_events.changes.dtos.request;

import it.bugboard26.bugboard.entities.Change;
import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.entities.TitleChange;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.enums.ChangeType;
import lombok.Getter;

@Getter
public class TitleChangeRequest implements ChangeRequest {
    private ChangeType type = ChangeType.TITLE;
    private String newTitle;

    @Override
    public Change mapToEntity(Issue issue, User author) {
        TitleChange change = new TitleChange();
        change.setOldTitle(issue.getTitle());
        change.setNewTitle(this.newTitle);
        change.setIssue(issue);
        change.setAuthor(author);
        return change;
    }
    
}
