package it.bugboard26.bugboard.modules.issue_events;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import it.bugboard26.bugboard.entities.Comment;
import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.entities.IssueEvent;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.modules.issue_events.comments.CommentRepository;
import it.bugboard26.bugboard.modules.issue_events.comments.CommentRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EventService {
    private EventRepository eventRepository;
    private CommentRepository commentRepository;

    public IssueEvent save(IssueEvent event) {
        return eventRepository.save(event);
    }

    public List<IssueEvent> getByIssueUuid(UUID uuid_issue) {
        return eventRepository.findByIssueUuid(uuid_issue);
    }

    public Comment saveComment(CommentRequest commentRequest, Issue issue, User author) {
        Comment newComment = CommentRequest.mapToEntity(commentRequest, issue, author);
        return eventRepository.save(newComment);
    }
    
    
}
