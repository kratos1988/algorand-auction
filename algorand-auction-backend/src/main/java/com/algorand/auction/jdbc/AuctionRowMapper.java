package com.algorand.auction.jdbc;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuctionRowMapper implements RowMapper<AuctionDto> {
    @Override
    public AuctionDto mapRow(ResultSet resultSet, int i) throws SQLException {
        return new AuctionDto(
                resultSet.getInt("ID"),
                resultSet.getString("ITEM_NAME"),
                resultSet.getString("DESCRIPTION"),
                resultSet.getString("TITLE"),
                resultSet.getBigDecimal("INITIAL_VALUE"),
                resultSet.getTimestamp("EXPIRATION_DATE").toLocalDateTime(),
                resultSet.getString("IMAGE_URL")
        );
    }
}
