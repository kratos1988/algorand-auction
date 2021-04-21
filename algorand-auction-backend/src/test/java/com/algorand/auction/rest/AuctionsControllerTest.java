package com.algorand.auction.rest;

import com.algorand.auction.model.Auction;
import com.algorand.auction.model.Bid;
import com.algorand.auction.usecase.RetrieveAuctionsUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.math.BigDecimal;

import static com.algorand.auction.model.AuctionBuilder.anAuction;
import static com.algorand.auction.model.BidBuilder.aBid;
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
        Bid bid =
                aBid()
                        .withAmount(new BigDecimal("11.99"))
                        .withAuctionId(1)
                        .withStatus("INSERTED")
                        .withUserId("AN_USER_ID")
                        .build();

        Auction auction =
                anAuction()
                        .withId(1)
                        .withItemName("AN_ITEM_NAME")
                        .withItemDescription("AN_ITEM_DESCRIPTION")
                        .withBids(bid)
                        .build();

        when(useCase.retrieveAll()).thenReturn(singletonList(auction));

        mockMvc.perform(get("/auctions/all").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(readJson("json/get_all_auctions_response.json")));
    }

    @Test
    void getAnAuction() throws Exception {
        Bid aBid =
                aBid()
                        .withAmount(new BigDecimal("11.99"))
                        .withAuctionId(1)
                        .withStatus("INSERTED")
                        .withUserId("AN_USER_ID")
                        .build();

        Bid anotherBid =
                aBid()
                        .withAmount(new BigDecimal("13.99"))
                        .withAuctionId(1)
                        .withStatus("INSERTED")
                        .withUserId("ANOTHER_USER_ID")
                        .build();

        Auction auction =
                anAuction()
                        .withId(1)
                        .withItemName("AN_ITEM_NAME")
                        .withItemDescription("AN_ITEM_DESCRIPTION")
                        .withBids(aBid, anotherBid)
                        .build();

        when(useCase.retrieveBy(1)).thenReturn(auction);

        mockMvc.perform(get("/auctions/1").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(readJson("json/get_auction_response.json")));
    }

    private String readJson(String fileName) throws IOException {
        return new String(getClass().getClassLoader().getResourceAsStream(fileName).readAllBytes());
    }
}