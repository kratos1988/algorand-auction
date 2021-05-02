package com.algorand.auction.jdbc;

import com.algorand.auction.model.FailureError;
import com.algorand.auction.model.Item;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

class JdbcAuctionRepositoryTest {

    public static final LocalDateTime EXPIRATION_DATE = LocalDateTime.of(2021, 4, 12, 7, 20);

    private NamedParameterJdbcTemplate jdbcTemplate;
    private JdbcAuctionRepository underTest;

    @BeforeEach
    void setUp() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(H2)
                .addScript("sql/schema.sql")
                .addScript("jdbc/test-data.sql")
                .build();

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        underTest = new JdbcAuctionRepository(jdbcTemplate);
    }

    @Test
    void retrieveAll() {

        Either<FailureError, List<Item>> result = underTest.retrieveAll();

        assertTrue(result.isRight());
        List<Item> retrievedItems = result.get();

        assertThat(retrievedItems, hasSize(1));

        Item item = retrievedItems.get(0);

        assertThat(item.getId(), equalTo(1));
        assertThat(item.getInitialValue(), equalTo(new BigDecimal("10.99")));
        assertThat(item.getItemName(), equalTo("AN_ITEM_NAME"));
        assertThat(item.getDescription(), equalTo("AN_ITEM_DESCRIPTION"));
        assertThat(item.getTitle(), equalTo("A_TITLE"));
        assertThat(item.getExpirationDate(), is(equalTo(EXPIRATION_DATE)));
        assertThat(item.getImageUrl(), equalTo("AN_IMAGE_URL"));

    }

    @Test
    void retrieveById() {

        Either<FailureError, Item> result = underTest.retrieveBy(1);

        assertTrue(result.isRight());
        Item item = result.get();

        assertThat(item.getId(), equalTo(1));
        assertThat(item.getInitialValue(), equalTo(new BigDecimal("10.99")));
        assertThat(item.getItemName(), equalTo("AN_ITEM_NAME"));
        assertThat(item.getDescription(), equalTo("AN_ITEM_DESCRIPTION"));
        assertThat(item.getTitle(), equalTo("A_TITLE"));
        assertThat(item.getExpirationDate(), is(equalTo(EXPIRATION_DATE)));
        assertThat(item.getImageUrl(), equalTo("AN_IMAGE_URL"));
        assertThat(item.getUserId(), equalTo(100));

    }

    @Test
    void retrieveExpired() {


        Either<FailureError, List<Item>> result = underTest.retrieveExpired();
        assertTrue(result.isRight());
        List<Item> expiredItems = result.get();

        assertThat(expiredItems, hasSize(1));

        Item expiredItem = expiredItems.get(0);

        assertThat(expiredItem.getId(), equalTo(1));
        assertThat(expiredItem.getInitialValue(), equalTo(new BigDecimal("10.99")));
        assertThat(expiredItem.getItemName(), equalTo("AN_ITEM_NAME"));
        assertThat(expiredItem.getDescription(), equalTo("AN_ITEM_DESCRIPTION"));
        assertThat(expiredItem.getTitle(), equalTo("A_TITLE"));
        assertThat(expiredItem.getExpirationDate(), is(equalTo(EXPIRATION_DATE)));
        assertThat(expiredItem.getImageUrl(), equalTo("AN_IMAGE_URL"));
        assertThat(expiredItem.getUserId(), equalTo(100));

    }
}