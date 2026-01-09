package it.bugboard26.bugboard;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import it.bugboard26.bugboard.modules.auth.JwtService;
import it.bugboard26.bugboard.modules.projects.HeaderRequestService;

import static org.mockito.Mockito.*;

@SpringBootTest
class BugboardApplicationTests {
	
	HeaderRequestService mockedHeaderRequestService = mock(HeaderRequestService.class);
	JwtService mockedJwtService = mock(JwtService.class);
	
	
	@Test
	void UpdateIssueWithViewer() {
		when(mockedHeaderRequestService.hasAuthorization()).thenReturn(true);
		when(mockedJwtService.parseToken("fakeRawToken")).thenReturn();
		
	}

}
