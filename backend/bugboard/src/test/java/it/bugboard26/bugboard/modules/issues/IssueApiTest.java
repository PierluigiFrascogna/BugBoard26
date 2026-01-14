package it.bugboard26.bugboard.modules.issues;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
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
import java.util.UUID;

import it.bugboard26.bugboard.auth.Jwt;
import it.bugboard26.bugboard.entities.Issue;
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

        MvcResult result = mvc.perform(get("/projects/{projectUuid}/issues", "ciaociaociao"))
            .andExpect(status().isBadRequest())
            .andReturn();

        assertTrue(result.getResolvedException() instanceof MethodArgumentTypeMismatchException);
    }

    @Test
    void getIssuesByProject_NotFoundProjectUUID() {
        UUID projectUUID = UUID.fromString("00000000-0000-0000-0000-000000000001");
        when(projectService.getByUuid(projectUUID)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

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
        String type = "BUG";
        String priority = "LOW";
        String state = "TODO";
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
        String type = "FEATURE";
        String priority = "HIGH";
        String state = "DONE";
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
        String type = "DOCUMENTATION";
        String priority = null;
        String state = null;
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
        String type = null;
        String priority = null;
        String state = null;
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
            () -> assertEquals(4, result.size()),
            () -> assertEquals(fakeIssueResponseList, result)
        );
    }

    @Test
    void postNewIssue_InvalidProjectUUID() throws Exception {
        MockMvc mvc = MockMvcBuilders.standaloneSetup(issueApi).build();

        MvcResult result = mvc.perform(post("/projects/{projectUuid}/issues", "ciaociaociao"))
            .andExpect(status().isBadRequest())
            .andReturn();

        assertTrue(result.getResolvedException() instanceof MethodArgumentTypeMismatchException);
    }

    @Test
    void postNewIssue_NotFoundProjectUUID() {
        UUID projectUUID = UUID.fromString("00000000-0000-0000-0000-000000000001");
        IssueRequest issueRequest = new IssueRequest();

        when(jwt.getRole()).thenReturn(Role.ADMIN);
        when(userService.getByUuid(any())).thenReturn(null);
        when(issueService.createIssue(any(), any(), any())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            issueApi.postNewIssue(projectUUID, issueRequest);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void postNewIssue_InvalidIssueRequest() throws Exception {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        MockMvc mvc = MockMvcBuilders.standaloneSetup(issueApi).setValidator(validator).build();

        String json = """
            {
                "description": "This is a test description",
                "type": "BUG",
                "priority": "HIGH"
            }
            """;

        MvcResult result = mvc.perform(post("/projects/{projectUuid}/issues", "00000000-0000-0000-0000-000000000001")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isBadRequest())
        .andReturn();

        assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException);
    }

    @Test
    void postNewIssue_TC1() {
        UUID projectUUID = UUID.fromString("00000000-0000-0000-0000-000000000001");
        IssueRequest issueRequest = new IssueRequest();
        Issue fakeIssue = new Issue();
        
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

        assertEquals(issueResponse.toString(), issueApi.postNewIssue(projectUUID, issueRequest).toString());
    }
}