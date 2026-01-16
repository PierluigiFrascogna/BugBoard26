package it.bugboard26.bugboard.modules.issues;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import it.bugboard26.bugboard.auth.Jwt;
import it.bugboard26.bugboard.entities.Issue;
import it.bugboard26.bugboard.entities.Project;
import it.bugboard26.bugboard.enums.Role;
import it.bugboard26.bugboard.modules.issues.dtos.IssueRequest;
import it.bugboard26.bugboard.modules.issues.dtos.IssueResponse;
import it.bugboard26.bugboard.modules.projects.ProjectService;
import it.bugboard26.bugboard.modules.users.UserService;
import it.bugboard26.bugboard.modules.users.dtos.UserResponse;

@ExtendWith(MockitoExtension.class)
class IssueApiTest {

    @InjectMocks
    private IssueApi issueApi;

    @Mock
    private Jwt jwt;

    @Mock
    private IssueService issueService;

    @Mock
    private UserService userService;

    @Mock
    private ProjectService projectService;

    @Test
    void getIssuesByProject_InvalidProjectUUID() throws Exception {
        MockMvc mvc = MockMvcBuilders.standaloneSetup(issueApi).build();

        String url = "/projects/{projectUuid}/issues";
        String invalidUUID = "invalid-uuid";
        mvc.perform(get(url, invalidUUID))
            .andExpect(status().isBadRequest());

        verifyNoInteractions(issueService);
        verifyNoInteractions(projectService);
    }

    @Test
    void getIssuesByProject_NotFoundProjectUUID() {
        UUID projectUUID = UUID.fromString("00000000-0000-0000-0000-000000000001");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            issueApi.getIssuesByProject(projectUUID, "BUG", "LOW", "PENDING");
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void getIssuesByProject_InvalidType() {
        UUID projectUUID = UUID.randomUUID();
        Project fakeProject = new Project();

        when(projectService.getByUuid(projectUUID)).thenReturn(Optional.of(fakeProject));
        when(issueService.getIssuesByProject(fakeProject, "err", "LOW", null)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            issueApi.getIssuesByProject(projectUUID, "err", "LOW", null);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void getIssuesByProject_InvalidPriority() {
        UUID projectUUID = UUID.randomUUID();
        Project fakeProject = new Project();

        when(projectService.getByUuid(projectUUID)).thenReturn(Optional.of(fakeProject));
        when(issueService.getIssuesByProject(fakeProject, "BUG", "err", null)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            issueApi.getIssuesByProject(projectUUID, "BUG", "err", null);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void getIssuesByProject_InvalidState() {
        UUID projectUUID = UUID.randomUUID();
        Project fakeProject = new Project();

        when(projectService.getByUuid(projectUUID)).thenReturn(Optional.of(fakeProject));
        when(issueService.getIssuesByProject(fakeProject, "BUG", "LOW", "err")).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

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
        Project fakeProject = new Project();       
        String type = "BUG";
        String priority = "LOW";
        String state = "TODO";

        when(projectService.getByUuid(projectUUID)).thenReturn(Optional.of(fakeProject));
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
        ResponseEntity<List<IssueResponse>> result = issueApi.getIssuesByProject(projectUUID, type, priority, state);
        
        // ASSERT    
        assertAll(
            () -> assertEquals(HttpStatus.OK, result.getStatusCode()),
            () -> assertEquals(1, result.getBody().size()),
            () -> assertEquals(fakeIssueResponseList.get(0), result.getBody().get(0))
        );
    }

    @Test
    void getIssuesByProject_TC2() {
        // ARRANGE
        IssueServiceMock issueServiceMock = new IssueServiceMock();
        List<IssueResponse> fakeIssueResponseList = issueServiceMock.issueResponseList;
        UUID projectUUID = UUID.randomUUID();         
        Project fakeProject = new Project();       
        String type = "QUESTION";
        String priority = "MEDIUM";
        String state = "PENDING";

        when(projectService.getByUuid(projectUUID)).thenReturn(Optional.of(fakeProject));

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
        ResponseEntity<List<IssueResponse>> result = issueApi.getIssuesByProject(projectUUID, type, priority, state);
        
        // ASSERT    
        assertAll(
            () -> assertEquals(HttpStatus.OK, result.getStatusCode()),
            () -> assertEquals(1, result.getBody().size()),
            () -> assertEquals(fakeIssueResponseList.get(1), result.getBody().get(0))
        );
    }

    @Test
    void getIssuesByProject_TC3() {
        // ARRANGE
        IssueServiceMock issueServiceMock = new IssueServiceMock();
        List<IssueResponse> fakeIssueResponseList = issueServiceMock.issueResponseList;
        UUID projectUUID = UUID.randomUUID();  
        Project fakeProject = new Project();       

        String type = "FEATURE";
        String priority = "HIGH";
        String state = "DONE";

        when(projectService.getByUuid(projectUUID)).thenReturn(Optional.of(fakeProject));

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
        ResponseEntity<List<IssueResponse>> result = issueApi.getIssuesByProject(projectUUID, type, priority, state);
        
        // ASSERT    
        assertAll(
            () -> assertEquals(HttpStatus.OK, result.getStatusCode()),
            () -> assertEquals(1, result.getBody().size()),
            () -> assertEquals(fakeIssueResponseList.get(2), result.getBody().get(0))
        );
    }

    @Test
    void getIssuesByProject_TC4() {
        // ARRANGE
        IssueServiceMock issueServiceMock = new IssueServiceMock();
        List<IssueResponse> fakeIssueResponseList = issueServiceMock.issueResponseList;
        UUID projectUUID = UUID.randomUUID();         
        Project fakeProject = new Project();       
        String type = "DOCUMENTATION";
        String priority = null;
        String state = null;

        when(projectService.getByUuid(projectUUID)).thenReturn(Optional.of(fakeProject));

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
        ResponseEntity<List<IssueResponse>> result = issueApi.getIssuesByProject(projectUUID, type, priority, state);
        
        // ASSERT    
        assertAll(
            () -> assertEquals(HttpStatus.OK, result.getStatusCode()),
            () -> assertEquals(1, result.getBody().size()),
            () -> assertEquals(fakeIssueResponseList.get(3), result.getBody().get(0))
        );
    }

    @Test
    void getIssuesByProject_TC5() {
        // ARRANGE
        IssueServiceMock issueServiceMock = new IssueServiceMock();
        List<IssueResponse> fakeIssueResponseList = issueServiceMock.issueResponseList;
        UUID projectUUID = UUID.randomUUID();         
        Project fakeProject = new Project();
        String type = null;
        String priority = null;
        String state = null;

        when(projectService.getByUuid(projectUUID)).thenReturn(Optional.of(fakeProject));

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
        ResponseEntity<List<IssueResponse>> result = issueApi.getIssuesByProject(projectUUID, type, priority, state);
        
        // ASSERT    
        assertAll(
            () -> assertEquals(HttpStatus.OK, result.getStatusCode()),
            () -> assertEquals(4, result.getBody().size()),
            () -> assertEquals(fakeIssueResponseList, result.getBody())
        );
    }

    @Test
    void postNewIssue_InvalidProjectUUID() throws Exception {
        MockMvc mvc = MockMvcBuilders.standaloneSetup(issueApi).build();

        String url = "/projects/{projectUuid}/issues";
        String invalidUUID = "invalid-uuid";

        mvc.perform(post(url, invalidUUID))
            .andExpect(status().isBadRequest());

        verifyNoInteractions(userService);
        verifyNoInteractions(projectService);
        verifyNoInteractions(issueService);
    }

    @Test
    void postNewIssue_NotFoundProjectUUID() {
        UUID projectUUID = UUID.randomUUID();
        IssueRequest issueRequest = new IssueRequest();

        when(jwt.getRole()).thenReturn(Role.ADMIN);
        when(projectService.getByUuid(projectUUID)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            issueApi.postNewIssue(projectUUID, issueRequest);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void postNewIssue_InvalidIssueRequest() throws Exception {
        MockMvc mvc = MockMvcBuilders.standaloneSetup(issueApi).build();
        String url = "/projects/{projectUuid}/issues";
        UUID projectUUID = UUID.randomUUID();

        String json = """
            {
                "description": "This is a test description",
                "type": "BUG",
                "priority": "HIGH"
            }
            """;

        mvc.perform(post(url, projectUUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isBadRequest());

        verifyNoInteractions(userService);
        verifyNoInteractions(projectService);
        verifyNoInteractions(issueService);
    }

    @Test
    void postNewIssue_TC1() {
        UUID projectUUID = UUID.randomUUID();
        Project fakeProject = new Project();
        IssueRequest issueRequest = new IssueRequest();
        Issue fakeIssue = new Issue();

        when(projectService.getByUuid(projectUUID)).thenReturn(Optional.of(fakeProject));
        
        Claims fakeClaims = mock(Claims.class);
        when(fakeClaims.getSubject()).thenReturn("00000000-0000-0000-0000-000000000002");
        when(fakeClaims.get("name", String.class)).thenReturn("Mario");
        when(fakeClaims.get("surname", String.class)).thenReturn("Rossi");
        when(fakeClaims.get("email", String.class)).thenReturn("mario.rossi@example.com");
        when(fakeClaims.get("role", String.class)).thenReturn("ADMIN");
        Jws<Claims> fakeJws = (Jws<Claims>) mock(Jws.class);
        when(fakeJws.getPayload()).thenReturn(fakeClaims);
        when(jwt.getToken()).thenReturn(fakeJws);
        IssueResponse issueResponse = IssueResponse.map(fakeIssue, new UserResponse(jwt.getToken()));
        
        when(jwt.getRole()).thenReturn(Role.ADMIN);
        when(userService.getByUuid(any())).thenReturn(null);
        when(issueService.createIssue(any(), any(), any())).thenReturn(fakeIssue);

        assertAll(
            () -> assertEquals(HttpStatus.CREATED, issueApi.postNewIssue(projectUUID, issueRequest).getStatusCode()),
            () -> assertEquals(issueResponse.toString(), issueApi.postNewIssue(projectUUID, issueRequest).getBody().toString())
        );
    }
}