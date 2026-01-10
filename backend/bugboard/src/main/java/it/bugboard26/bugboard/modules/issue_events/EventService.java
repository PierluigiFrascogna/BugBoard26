package it.bugboard26.bugboard.modules.issue_events;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import it.bugboard26.bugboard.entities.Change;
import it.bugboard26.bugboard.entities.Comment;
import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.entities.IssueEvent;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.microservices.users.UsersMicroservice;
import it.bugboard26.bugboard.modules.issue_events.changes.dtos.request.ChangeRequest;
import it.bugboard26.bugboard.modules.issue_events.comments.dtos.CommentRequest;
import it.bugboard26.bugboard.modules.issue_events.dtos.IssueEventResponse;
import it.bugboard26.bugboard.modules.issues.IssueService;
import it.bugboard26.bugboard.modules.users.dtos.UserResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EventService {
    private EventRepository eventRepository;
    private IssueService issueService;
    private UsersMicroservice usersMicroService;

    public IssueEvent save(IssueEvent event) {
        return eventRepository.save(event);
    }

    public List<IssueEvent> getByIssueUuid(UUID uuid_issue) {
        return eventRepository.findByIssueUuidOrderByCreatedAt(uuid_issue);
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

    public List<IssueEventResponse> enrichEventsWithAuthors(List<IssueEvent> events) {
        if (events.isEmpty()) return new ArrayList<>();

        Set<UUID> authorIds = events.stream()
                .map(i -> i.getAuthor().getUuid())
                .collect(Collectors.toSet());

        Map<UUID, UserResponse> authors = usersMicroService.getUsersByIds(authorIds);

        return events.stream()
                .map(event -> {
                    UserResponse author = authors.get(event.getAuthor().getUuid());
                    return IssueEventResponse.map(event, author);
                })
                .collect(Collectors.toList());
    }
    
    
}
