package com.algorand.auction.rest;

import com.algorand.auction.rest.response.AuthenticationResponse;
import com.algorand.auction.usecase.RetrieveUserDataUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RetrieveUserDataUseCase useCase;

    @Test
    void retrieveTokenForValidUser() throws Exception {

        String token = "A_TOKEN";
        when(useCase.authenticate("username", "password")).thenReturn(new AuthenticationResponse("username", token));

        mockMvc.perform(
                post("/api/authenticate")
                        .content(readJson("json/authentication_request.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(readJson("json/authentication_response.json")));
    }

    @Test
    void unauthorizeUser() throws Exception {

        when(useCase.authenticate("username", "password")).thenReturn(null);

        mockMvc.perform(
                post("/api/authenticate")
                        .content(readJson("json/authentication_request.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

    private String readJson(String fileName) throws IOException {
        return new String(getClass().getClassLoader().getResourceAsStream(fileName).readAllBytes());
    }
}