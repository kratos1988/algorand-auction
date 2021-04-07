package com.algorand.auction.rest;

import com.algorand.auction.usecase.RetrieveAuctionsUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.algorand.auction.model.AuctionBuilder.anAuction;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GetAuctionsController.class)
class GetAuctionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RetrieveAuctionsUseCase useCase;

    @Test
    void getAllAuctions() throws Exception {

        when(useCase.retrieveAll()).thenReturn(singletonList(anAuction().withName("A_NAME").build()));
        mockMvc.perform(get("/auctions").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));
    }
}