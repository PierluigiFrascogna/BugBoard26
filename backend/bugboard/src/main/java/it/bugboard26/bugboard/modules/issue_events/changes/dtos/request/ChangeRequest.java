package it.bugboard26.bugboard.modules.issue_events.changes.dtos.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import it.bugboard26.bugboard.entities.Change;
import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.entities.User;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, 
    include = JsonTypeInfo.As.PROPERTY, 
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = TitleChangeRequest.class, name = "TITLE"),
    @JsonSubTypes.Type(value = DescriptionChangeRequest.class, name = "DESCRIPTION"),
    @JsonSubTypes.Type(value = PriorityChangeRequest.class, name = "PRIORITY"),
    @JsonSubTypes.Type(value = StateChangeRequest.class, name = "STATE")
})
public interface ChangeRequest {
    Change mapToEntity(Issue issue, User author);
}
