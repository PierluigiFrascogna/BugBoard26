package it.bugboard26.bugboard.modules.issue_events;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import it.bugboard26.bugboard.entities.Change;
import it.bugboard26.bugboard.entities.Comment;
import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.entities.IssueEvent;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.modules.issue_events.changes.dtos.request.ChangeRequest;
import it.bugboard26.bugboard.modules.issue_events.comments.CommentRequest;
import it.bugboard26.bugboard.modules.issues.IssueService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EventService {
    private EventRepository eventRepository;
    private IssueService issueService;

    public IssueEvent save(IssueEvent event) {
        return eventRepository.save(event);
    }

    public List<IssueEvent> getByIssueUuid(UUID uuid_issue) {
        return eventRepository.findByIssueUuid(uuid_issue);
    }

    public Comment saveComment(CommentRequest request, Issue issue, User author) {
        Comment newComment = CommentRequest.mapToEntity(request, issue, author);
        return eventRepository.save(newComment);
    }

    public Change saveChange(ChangeRequest request, Issue issue, User author) {
        Change change = request.mapToEntity(issue, author);
        Issue updatedIssue = change.apply(issue);
        issueService.save(updatedIssue);
        return eventRepository.save(change);
    }
    
    
}
