package it.bugboard26.bugboard.modules.issues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.entities.Project;
import it.bugboard26.bugboard.entities.User;
import it.bugboard26.bugboard.enums.IssueState;
import it.bugboard26.bugboard.enums.IssueType;
import it.bugboard26.bugboard.enums.Priority;
import it.bugboard26.bugboard.modules.issues.dtos.IssueResponse;
import it.bugboard26.bugboard.modules.users.dtos.UserResponse;

public class IssueServiceMock {
    
    public List<Issue> issueList = new ArrayList<>(List.of(
        new Issue(
            UUID.fromString("11111111-1111-1111-1111-111111111111"),
            "Issue 1",
            "Description for Issue 1",
            LocalDate.now(), 
            IssueType.BUG,
            Priority.LOW, 
            IssueState.TODO,
            null, 
            new User(), 
            new Project(), 
            new HashSet<>()
        ), 
        new Issue(
            UUID.fromString("22222222-2222-2222-2222-222222222222"), 
            "Issue 2", 
            "Description for Issue 2", 
            LocalDate.now(), 
            IssueType.QUESTION, 
            Priority.MEDIUM, 
            IssueState.PENDING, 
            null, 
            new User(), 
            new Project(), 
            new HashSet<>()
        ),
        new Issue(
            UUID.fromString("33333333-3333-3333-3333-333333333333"), 
            "Issue 3", 
            "Description for Issue 3", 
            LocalDate.now(), 
            IssueType.FEATURE, 
            Priority.HIGH, 
            IssueState.DONE, 
            null, 
            new User(), 
            new Project(), 
            new HashSet<>()
        ),

        new Issue(
            UUID.fromString("44444444-4444-4444-4444-444444444444"), 
            "Issue 4", 
            "Description for Issue 4", 
            LocalDate.now(), 
            IssueType.DOCUMENTATION, 
            Priority.LOW,
            IssueState.TODO, 
            null, 
            new User(), 
            new Project(), 
            new HashSet<>()
        )
    ));

    List<IssueResponse> issueResponseList = issueList.stream()
        .map(issue -> IssueResponse.map(issue, new UserResponse()))
        .collect(Collectors.toList());


    public List<Issue> getIssuesByProject(String type, String priority, String state) {
        return issueList.stream().filter(
            issue -> 
                (type == null || type.equals(issue.getType().toString())) &&
                (priority == null || priority.equals(issue.getPriority().toString())) &&
                (state == null || state.equals(issue.getState().toString()))
        ).toList();
    }

    public List<IssueResponse> enrichIssuesWithAuthors(String type, String priority, String state) {
        return issueResponseList.stream().filter(
            issue -> 
                (type == null || type.equals(issue.getType().toString())) &&
                (priority == null || priority.equals(issue.getPriority().toString())) &&
                (state == null || state.equals(issue.getState().toString()))
        ).toList();
    }
}