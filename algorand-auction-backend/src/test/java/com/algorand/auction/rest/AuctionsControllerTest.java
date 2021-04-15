package com.algorand.auction.rest;

import com.algorand.auction.model.Auction;
import com.algorand.auction.usecase.RetrieveAuctionsUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static com.algorand.auction.model.AuctionBuilder.anAuction;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuctionsController.class)
class AuctionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RetrieveAuctionsUseCase useCase;

    @Test
    void getAllAuctions() throws Exception {
        Auction auction =
                anAuction()
                        .withId(1)
                        .withItemName("AN_ITEM_NAME")
                        .withItemDescription("AN_ITEM_DESCRIPTION")
                        .withBids(emptyList())
                        .build();
        when(useCase.retrieveAll()).thenReturn(singletonList(auction));
        mockMvc.perform(get("/auctions/all").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(readJson("json/get_all_auctions_response.json")));
    }

    private String readJson(String fileName) throws IOException {
        return new String(getClass().getClassLoader().getResourceAsStream(fileName).readAllBytes());
    }
}