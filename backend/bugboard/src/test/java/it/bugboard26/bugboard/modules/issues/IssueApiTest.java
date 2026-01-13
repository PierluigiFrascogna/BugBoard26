package it.bugboard26.bugboard.modules.issues;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import it.bugboard26.bugboard.auth.JwtService;
import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.enums.IssueState;
import it.bugboard26.bugboard.enums.IssueType;
import it.bugboard26.bugboard.enums.Priority;
import it.bugboard26.bugboard.modules.issues.dtos.IssueResponse;
import it.bugboard26.bugboard.modules.users.UserService;

@ExtendWith(MockitoExtension.class)
class IssueApiTest {

    @InjectMocks
    private IssueApi issueApi;

    @Mock
    private JwtService jwtService;

    @Mock
    private IssueService issueService;

    @Mock
    private UserService userService;

    @Test
    void getIssuesByProject_NullProjectUUID() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            issueApi.getIssuesByProject(null, null, null, null);
        });
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
    }

    @Test
    void getIssuesByProject_InvalidProjectUUID() {
        UUID projectUUID = UUID.fromString("invalid-uuid-string");
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            issueApi.getIssuesByProject(projectUUID, null, null, null);
        });
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
    }

    @Test
    void getIssuesByProject_NotFoundProjectUUID() {
        UUID projectUUID = UUID.fromString("00000000-0000-0000-0000-0000000000001");
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            issueApi.getIssuesByProject(projectUUID, "BUG", "LOW", "PENDING");
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void getIssuesByProject_InvalidType() {
        UUID projectUUID = UUID.randomUUID();
        when(issueService.getIssuesByProject(projectUUID, "err", "LOW", null)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            issueApi.getIssuesByProject(projectUUID, "err", "LOW", null);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void getIssuesByProject_InvalidPriority() {
        UUID projectUUID = UUID.randomUUID();
        when(issueService.getIssuesByProject(projectUUID, "BUG", "err", null)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            issueApi.getIssuesByProject(projectUUID, "BUG", "err", null);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void getIssuesByProject_InvalidState() {
        UUID projectUUID = UUID.randomUUID();
        when(issueService.getIssuesByProject(projectUUID, "BUG", "LOW", "err")).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            issueApi.getIssuesByProject(projectUUID, "BUG", "LOW", "err");
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void getIssuesByProject_TC1() {
        // ARRANGE
        IssueServiceMock issueServiceMock = new IssueServiceMock();
        List<IssueResponse> fakeIssueResponseList = issueServiceMock.issueResponseList;
        UUID projectUUID = UUID.randomUUID();         
        String type = "x";
        String priority = "x";
        String state = "x";
        when(issueService.getIssuesByProject(any(), any(), any(), any())).thenAnswer(
            inv -> issueServiceMock.getIssuesByProject(
                type,
                priority,
                state
            )
        );

        when(issueService.enrichIssuesWithAuthors(any())).thenAnswer(
            inv -> issueServiceMock.enrichIssuesWithAuthors(
                type, 
                priority,
                state
            )
        );

        // ACT
        List<IssueResponse> result = issueApi.getIssuesByProject(projectUUID, type, priority, state);
        
        // ASSERT    
        assertAll(
            () -> assertEquals(1, result.size()),
            () -> assertEquals(fakeIssueResponseList.get(0), result.get(0))
        );
    }

    @Test
    void getIssuesByProject_TC2() {
        // ARRANGE
        IssueServiceMock issueServiceMock = new IssueServiceMock();
        List<IssueResponse> fakeIssueResponseList = issueServiceMock.issueResponseList;
        UUID projectUUID = UUID.randomUUID();         
        String type = "QUESTION";
        String priority = "MEDIUM";
        String state = "PENDING";
        when(issueService.getIssuesByProject(any(), any(), any(), any())).thenAnswer(
            inv -> issueServiceMock.getIssuesByProject(
                type,
                priority,
                state
            )
        );

        when(issueService.enrichIssuesWithAuthors(any())).thenAnswer(
            inv -> issueServiceMock.enrichIssuesWithAuthors(
                type, 
                priority,
                state
            )
        );

        // ACT
        List<IssueResponse> result = issueApi.getIssuesByProject(projectUUID, type, priority, state);
        
        // ASSERT    
        assertAll(
            () -> assertEquals(1, result.size()),
            () -> assertEquals(fakeIssueResponseList.get(1), result.get(0))
        );
    }

    @Test
    void getIssuesByProject_TC3() {
        // ARRANGE
        IssueServiceMock issueServiceMock = new IssueServiceMock();
        List<IssueResponse> fakeIssueResponseList = issueServiceMock.issueResponseList;
        UUID projectUUID = UUID.randomUUID();         
        String type = "x";
        String priority = "x";
        String state = "x";
        when(issueService.getIssuesByProject(any(), any(), any(), any())).thenAnswer(
            inv -> issueServiceMock.getIssuesByProject(
                type,
                priority,
                state
            )
        );

        when(issueService.enrichIssuesWithAuthors(any())).thenAnswer(
            inv -> issueServiceMock.enrichIssuesWithAuthors(
                type, 
                priority,
                state
            )
        );

        // ACT
        List<IssueResponse> result = issueApi.getIssuesByProject(projectUUID, type, priority, state);
        
        // ASSERT    
        assertAll(
            () -> assertEquals(1, result.size()),
            () -> assertEquals(fakeIssueResponseList.get(2), result.get(0))
        );
    }

    @Test
    void getIssuesByProject_TC4() {
        // ARRANGE
        IssueServiceMock issueServiceMock = new IssueServiceMock();
        List<IssueResponse> fakeIssueResponseList = issueServiceMock.issueResponseList;
        UUID projectUUID = UUID.randomUUID();         
        String type = "x";
        String priority = "x";
        String state = "x";
        when(issueService.getIssuesByProject(any(), any(), any(), any())).thenAnswer(
            inv -> issueServiceMock.getIssuesByProject(
                type,
                priority,
                state
            )
        );

        when(issueService.enrichIssuesWithAuthors(any())).thenAnswer(
            inv -> issueServiceMock.enrichIssuesWithAuthors(
                type, 
                priority,
                state
            )
        );

        // ACT
        List<IssueResponse> result = issueApi.getIssuesByProject(projectUUID, type, priority, state);
        
        // ASSERT    
        assertAll(
            () -> assertEquals(1, result.size()),
            () -> assertEquals(fakeIssueResponseList.get(3), result.get(0))
        );
    }

    @Test
    void getIssuesByProject_TC5() {
        // ARRANGE
        IssueServiceMock issueServiceMock = new IssueServiceMock();
        List<IssueResponse> fakeIssueResponseList = issueServiceMock.issueResponseList;
        UUID projectUUID = UUID.randomUUID();         
        String type = "x";
        String priority = "x";
        String state = "x";
        when(issueService.getIssuesByProject(any(), any(), any(), any())).thenAnswer(
            inv -> issueServiceMock.getIssuesByProject(
                type,
                priority,
                state
            )
        );

        when(issueService.enrichIssuesWithAuthors(any())).thenAnswer(
            inv -> issueServiceMock.enrichIssuesWithAuthors(
                type, 
                priority,
                state
            )
        );

        // ACT
        List<IssueResponse> result = issueApi.getIssuesByProject(projectUUID, type, priority, state);
        
        // ASSERT    
        assertAll(
            () -> assertEquals(1, result.size()),
            () -> assertEquals(fakeIssueResponseList, result)
        );
    }
}