package com.algorand.auction.rest;

import com.algorand.auction.jdbc.DatabaseError;
import com.algorand.auction.model.Auction;
import com.algorand.auction.model.Bid;
import com.algorand.auction.model.Item;
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
import java.time.LocalDateTime;

import static com.algorand.auction.model.AuctionBuilder.anAuction;
import static com.algorand.auction.model.BidBuilder.aBid;
import static com.algorand.auction.model.ItemBuilder.anItem;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuctionController.class)
class AuctionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RetrieveAuctionsUseCase useCase;

    @Test
    void getAllAuctions() throws Exception {

        Item item =
                anItem()
                        .withId(1)
                        .withTitle("Mona Lisa")
                        .withItemName("Mona Lisa's Painting")
                        .withDescription("One of Da Vinci's masterieces")
                        .withInitialValue(new BigDecimal("10.99"))
                        .withHighestBid(new BigDecimal("11"))
                        .withExpirationDate(LocalDateTime.of(2021, 4, 12, 7, 20))
                        .withImageUrl("https://www.everypainterpaintshimself.com/images/made/article_images_new/Mona_Lisa_Large_440_666.jpg")
                        .withUserId(100)
                        .withStatus("OPEN")
                        .build();

        when(useCase.retrieveAll()).thenReturn(right(singletonList(item)));

        mockMvc.perform(get("/api/auctions/all").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(readJson("json/get_all_auctions_response.json")));
    }

    @Test
    void getLastBids() throws Exception {

        Bid aBid =
                aBid()
                        .withAmount(new BigDecimal("11.99"))
                        .withAuctionId(1)
                        .withStatus("INSERTED")
                        .withUserId(1)
                        .build();

        Bid anotherBid =
                aBid()
                        .withAmount(new BigDecimal("13.99"))
                        .withAuctionId(1)
                        .withStatus("INSERTED")
                        .withUserId(2)
                        .build();
        when(useCase.retrieveLastBidsFor(1)).thenReturn(right(asList(aBid, anotherBid)));

        mockMvc.perform(get("/api/auctions/1/bids").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(readJson("json/get_last_bids_response.json")));
    }

    @Test
    void whenNoAuctions() throws Exception {

        when(useCase.retrieveAll()).thenReturn(left(new DatabaseError(new RuntimeException("an error"))));

        mockMvc.perform(get("/auctions/all").contentType(APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    void getAnAuction() throws Exception {
        Bid aBid =
                aBid()
                        .withAmount(new BigDecimal("11.99"))
                        .withAuctionId(1)
                        .withStatus("INSERTED")
                        .withUserId(1)
                        .build();

        Bid anotherBid =
                aBid()
                        .withAmount(new BigDecimal("13.99"))
                        .withAuctionId(1)
                        .withStatus("INSERTED")
                        .withUserId(2)
                        .build();

        Item item =
                anItem()
                        .withId(1)
                        .withTitle("Mona Lisa")
                        .withItemName("Mona Lisa's Painting")
                        .withDescription("One of Da Vinci's masterieces")
                        .withInitialValue(new BigDecimal("10.99"))
                        .withHighestBid(new BigDecimal("11"))
                        .withExpirationDate(LocalDateTime.of(2021, 4, 12, 7, 20))
                        .withImageUrl("https://www.everypainterpaintshimself.com/images/made/article_images_new/Mona_Lisa_Large_440_666.jpg")
                        .withUserId(100)
                        .withStatus("OPEN")
                        .build();

        Auction auction = anAuction().withBids(asList(aBid, anotherBid)).withItem(item).build();

        when(useCase.retrieveById(1)).thenReturn(right(auction));

        mockMvc.perform(get("/api/auctions/1").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(readJson("json/get_auction_response.json")));
    }

    @Test
    void whenNoAuction() throws Exception {

        when(useCase.retrieveById(1)).thenReturn(left(new DatabaseError(new RuntimeException("an error"))));

        mockMvc.perform(get("/auctions/1").contentType(APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    private String readJson(String fileName) throws IOException {
        return new String(getClass().getClassLoader().getResourceAsStream(fileName).readAllBytes());
    }
}