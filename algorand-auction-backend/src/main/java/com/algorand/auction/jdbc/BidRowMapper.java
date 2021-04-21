package com.algorand.auction.jdbc;

import com.algorand.auction.model.Bid;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BidRowMapper implements RowMapper<Bid> {
    @Override
    public Bid mapRow(ResultSet resultSet, int i) throws SQLException {
        Bid bid = new Bid();
        bid.setAmount(resultSet.getBigDecimal("AMOUNT"));
        bid.setStatus(resultSet.getString("STATUS"));
        bid.setAuctionId(resultSet.getInt("AUCTION_ID"));
        bid.setUserId(resultSet.getString("USER_ID"));
        return bid;
    }
}