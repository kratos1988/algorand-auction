SET MODE TO PostgreSQL;
insert into AUCTIONS (ID, ITEM_NAME, DESCRIPTION, TITLE, INITIAL_VALUE, STATUS, EXPIRATION_DATE,IMAGE_URL, USER_ID)
VALUES
(1,'Mona Lisa painting','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua','Mona Lisa',1936.27,'SOLD','2021-05-28 18:46','https://www.everypainterpaintshimself.com/images/made/article_images_new/Mona_Lisa_Large_440_666.jpg', 100),
(2,'The Persistence of Memory painting','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua','The Persistence of Memory',2214.27,'OPEN','2021-05-28 18:46','https://www.arteopereartisti.it/wp-content/uploads/2019/05/lpdm-00.jpg', 101);

insert into BIDS (ID, AUCTION_ID, AMOUNT, STATUS, USER_ID, INSERTION_DATE)
VALUES
(1,1,1937.27, 'ACCEPTED', 101, '2021-04-12 13:15');

insert into USERS (ID, USER_NAME, PUBLIC_KEY, PASSWORD) VALUES (100,'manuel_m','SGUNZ2UCYL3YD6XQ3H7O7ZJYDXSSVESQCYE5RBHWPHJ6NGZBN6C3EK7Y5U', 'manuel_m');
insert into USERS (ID, USER_NAME, PUBLIC_KEY, PASSWORD) VALUES (101,'luigi_c','F2EJTM2MHOCKJAV7AK3TE2SUPI7TPX3T2J4XSDSH2XKIDALT5FFKM3N7DA', 'luigi_c');
insert into USERS (ID, USER_NAME, PUBLIC_KEY, PASSWORD) VALUES (102,'christian_m','GD64YIY3TWGDMCNPP553DZPPR6LDUSFQOIJVFDPPXWEG3FVOJCCDBBHU5A', 'christian_m');