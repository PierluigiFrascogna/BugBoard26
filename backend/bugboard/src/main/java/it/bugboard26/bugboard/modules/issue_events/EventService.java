package it.bugboard26.bugboard.modules.issue_events;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import it.bugboard26.bugboard.entities.IssueEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EventService {
    private EventRepository eventRepository;

    public List<IssueEvent> getByIssueUuid(UUID uuid_issue) {
        return eventRepository.findByIssueUuid(uuid_issue);
    }
}
