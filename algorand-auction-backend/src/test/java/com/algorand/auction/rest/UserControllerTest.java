package com.algorand.auction.rest;

import com.algorand.auction.rest.request.CredentialsRequest;
import com.algorand.auction.rest.response.AuthenticationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAuthenticator useCase;

    @Test
    void retrieveTokenForValidUser() throws Exception {

        String token = "A_TOKEN";
        when(useCase.authenticate(new CredentialsRequest("username", "password")))
                .thenReturn(new AuthenticationResponse("username", token));

        mockMvc.perform(get("/login?username=username&password=password"))
                .andExpect(status().isOk())
                .andExpect(content().json(readJson("json/authentication_response.json")));
    }

    @Test
    void unauthorizeUser() throws Exception {

        when(useCase.authenticate(new CredentialsRequest("username", "password"))).thenReturn(null);

        mockMvc.perform(get("/login?username=username&password=password"))
                .andExpect(status().is(401));
    }

    private String readJson(String fileName) throws IOException {
        return new String(getClass().getClassLoader().getResourceAsStream(fileName).readAllBytes());
    }
}