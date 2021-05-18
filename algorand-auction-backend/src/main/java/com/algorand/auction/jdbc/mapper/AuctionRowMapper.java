package com.algorand.auction.jdbc.mapper;

import com.algorand.auction.model.Item;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuctionRowMapper implements RowMapper<Item> {
    @Override
    public Item mapRow(ResultSet resultSet, int i) throws SQLException {
        Item item = new Item();
        item.setId(resultSet.getInt("ID"));
        item.setItemName(resultSet.getString("ITEM_NAME"));
        item.setDescription(resultSet.getString("DESCRIPTION"));
        item.setTitle(resultSet.getString("TITLE"));
        item.setInitialValue(resultSet.getBigDecimal("INITIAL_VALUE"));
        item.setExpirationDate(resultSet.getTimestamp("EXPIRATION_DATE").toLocalDateTime());
        item.setImageUrl(resultSet.getString("IMAGE_URL"));
        item.setUserId(resultSet.getInt("USER_ID"));
        item.setStatus(resultSet.getString("STATUS"));
        return item;

    }
}
