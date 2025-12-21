package it.bugboard26.bugboard.modules.issue_events.changes.dtos.request;

import it.bugboard26.bugboard.entities.Change;
import it.bugboard26.bugboard.entities.DescriptionChange;
import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.enums.ChangeType;
import lombok.Getter;

@Getter
public class DescriptionChangeRequest implements ChangeRequest {
    private ChangeType type = ChangeType.DESCRIPTION;
    private String newDescription;
    
    @Override
    public Change mapToEntity(Issue issue, User author) {
        DescriptionChange change = new DescriptionChange();
        change.setOldDescription(issue.getDescription());
        change.setNewDescription(this.newDescription);
        change.setIssue(issue);
        change.setAuthor(author);
        return change;
    }
    
}
