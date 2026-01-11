package it.bugboard26.bugboard.modules.issues;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.bugboard26.bugboard.modules.auth.JwtService;

@ExtendWith(MockitoExtension.class)
class IssueApiTest {

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private IssueApi issueApi;

    @Test
    void getIssuesByProject_input_output() {

    }
}