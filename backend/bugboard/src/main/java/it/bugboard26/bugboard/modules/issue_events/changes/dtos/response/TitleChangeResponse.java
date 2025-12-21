package it.bugboard26.bugboard.modules.issue_events.changes.dtos.response;

import it.bugboard26.bugboard.entities.TitleChange;
import it.bugboard26.bugboard.users_micro_service.UserResponse;
import lombok.Getter;

@Getter
public class TitleChangeResponse extends ChangeResponse {
    private String oldTitle;
    private String newTitle;

    public static ChangeResponse map(TitleChange change, UserResponse author){
        TitleChangeResponse response = new TitleChangeResponse();
        response.createdAt = change.getCreatedAt();
        response.type = change.getType();
        response.changeType = change.getChangeType();
        response.author = author;
        response.oldTitle = change.getOldTitle();
        response.newTitle = change.getNewTitle();
        return response;
    }
    
}
