SET MODE PostgreSQL;
insert into AUCTIONS (ID, ITEM_NAME, DESCRIPTION, TITLE, INITIAL_VALUE, STATUS, EXPIRATION_DATE,IMAGE_URL, USER_ID)
VALUES
(1,'AN_ITEM_NAME','AN_ITEM_DESCRIPTION','A_TITLE',10.99,'SOLD','2021-04-12 07:20','AN_IMAGE_URL',100);

insert into BIDS (ID, AUCTION_ID, AMOUNT, STATUS, USER_ID) VALUES (1,1,20.99, 'ACCEPTED', 'AN_USER_ID');
insert into USERS (ID, USER_NAME, PUBLIC_KEY) VALUES (100,'AN_USER_ID','A_PUBLIC_KEY');