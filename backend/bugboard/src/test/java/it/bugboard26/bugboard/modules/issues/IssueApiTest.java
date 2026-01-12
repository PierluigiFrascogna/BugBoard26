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

import it.bugboard26.bugboard.auth.JwtService;
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
    void getIssuesByProject_TC6() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            issueApi.getIssuesByProject(null, null, null, null);
        });
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
        
    }
}