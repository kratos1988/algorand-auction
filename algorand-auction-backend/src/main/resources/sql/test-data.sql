SET MODE PostgreSQL;
insert into AUCTIONS (ID, ITEM_NAME, DESCRIPTION, TITLE, INITIAL_VALUE, STATUS, EXPIRATION_DATE,IMAGE_URL)
VALUES
(1,"Mona Lisa's painting",'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua','Mona Lisa',1936.27,'SOLD','2021-04-12 07:20','https://www.google.com/imgres?imgurl=https%3A%2F%2Fupload.wikimedia.org%2Fwikipedia%2Fcommons%2Fd%2Fd5%2FMona_Lisa_%2528copy%252C_Hermitage%2529.jpg&imgrefurl=https%3A%2F%2Fcommons.wikimedia.org%2Fwiki%2FFile%3AMona_Lisa_(copy%2C_Hermitage).jpg&tbnid=SzWFLg2Qw8oBQM&vet=12ahUKEwjb0LXZhJ7wAhUBkqQKHUexA10QMygBegUIARDBAQ..i&docid=-GBjMS6AwTJ14M&w=1433&h=1920&q=mona%20lisa%20painting%20free%20license&ved=2ahUKEwjb0LXZhJ7wAhUBkqQKHUexA10QMygBegUIARDBAQ');

insert into BIDS (ID, AUCTION_ID, AMOUNT, STATUS, USER_ID)
VALUES
(1,1,1937.27, 'ACCEPTED', 'manuel_m');

insert into USERS (ID, USER_NAME, PUBLIC_KEY) VALUES (1,'manuel_m','SGUNZ2UCYL3YD6XQ3H7O7ZJYDXSSVESQCYE5RBHWPHJ6NGZBN6C3EK7Y5U');