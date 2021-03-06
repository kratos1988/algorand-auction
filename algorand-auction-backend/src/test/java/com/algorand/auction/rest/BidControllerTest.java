package com.algorand.auction.rest;

import com.algorand.auction.usecase.BidAmountForItemUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.math.BigDecimal;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BidController.class)
class BidControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidAmountForItemUseCase useCase;

    @Test
    void placeBid() throws Exception {

        when(useCase.bid(BigDecimal.TEN, 1, "A_TOKEN")).thenReturn(right(null));
        mockMvc.perform(
                    post("/api/bid/place")
                        .contentType(APPLICATION_JSON)
                        .content(readJson("json/place_bid_request.json")))
                .andExpect(status().isCreated());

        verify(useCase, times(1)).bid(BigDecimal.TEN, 1, "A_TOKEN");
    }

    @Test
    void noPlacingBid() throws Exception {

        when(useCase.bid(BigDecimal.TEN, 1, "A_TOKEN")).thenReturn(left(null));
        mockMvc.perform(
                post("/api/bid/place")
                        .contentType(APPLICATION_JSON)
                        .content(readJson("json/place_bid_request.json")))
                .andExpect(status().isNotFound());

    }

    private String readJson(String fileName) throws IOException {
        return new String(getClass().getClassLoader().getResourceAsStream(fileName).readAllBytes());
    }
}