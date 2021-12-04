package movie.web.integration;

import movie.web.model.Role;
import movie.web.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorizationTokenIntegrationTest {
    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    private MockMvc mockMvc;

    protected void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void accessToResouce_whenNotAuthorized_then4xxError() throws Exception {
        setUp();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/film/save"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void ccessToResouce_whenAuthorized_then2xxStatus() throws Exception {
        setUp();
        Role role = Role.USER;
        String token = jwtTokenProvider.createToken("admin@mail.ru", role.name());
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/films").header("Authorization", token))
                .andExpect(status().is2xxSuccessful());
    }
}