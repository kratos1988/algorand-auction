package com.algorand.auction.rest;

import com.algorand.auction.jdbc.NoRecordError;
import com.algorand.auction.model.Transaction;
import com.algorand.auction.model.User;
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
import java.math.BigDecimal;

import static com.algorand.auction.model.TransactionBuilder.aTransaction;
import static com.algorand.auction.model.UserBuilder.anUser;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import static java.util.Arrays.asList;
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
    void authenticateUser() throws Exception {

        User buyer = anUser().withPublicKey("A_PUBLIC_KEY").withUserName("AN_USERNAME").build();
        User seller = anUser().withPublicKey("ANOTHER_PUBLIC_KEY").withUserName("ANOTHER_USERNAME").build();
        Transaction transaction = aTransaction()
                .withBuyer(buyer)
                .withSeller(seller)
                .withAmount(new BigDecimal("1936"))
                .build();
        String token = "A_TOKEN";
        when(useCase.authenticate("username", "password"))
                .thenReturn(right(new AuthenticationResponse("username", token, asList(transaction))));

        mockMvc.perform(
                post("/api/authenticate")
                        .content(readJson("json/authentication_request.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(readJson("json/authentication_response.json")));
    }

    @Test
    void unauthorizeUser() throws Exception {

        when(useCase.authenticate("username", "password")).thenReturn(left(new NoRecordError(0)));

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