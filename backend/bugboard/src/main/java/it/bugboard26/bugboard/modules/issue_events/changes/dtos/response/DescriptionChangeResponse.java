package it.bugboard26.bugboard.modules.issue_events.changes.dtos.response;

import it.bugboard26.bugboard.entities.DescriptionChange;
import it.bugboard26.bugboard.modules.users.dtos.UserResponse;
import lombok.Getter;

@Getter
public class DescriptionChangeResponse extends ChangeResponse {
    private String oldDescription;
    private String newDescription;

    public static ChangeResponse map(DescriptionChange change, UserResponse author){
        DescriptionChangeResponse response = new DescriptionChangeResponse();
        response.createdAt = change.getCreatedAt();
        response.type = change.getType();
        response.changeType = change.getChangeType();
        response.author = author;
        response.oldDescription = change.getOldDescription();
        response.newDescription = change.getNewDescription();
        return response;
    }
}
