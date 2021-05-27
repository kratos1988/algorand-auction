# algorand-auction
This project is created to demonstrate one of the possible applications which the Algorand blockchain could be used.
`Algorand-auction` is a Proof of Concept (PoC) for an auction website in which the exchange money are Algos. The full website is available at the following URL: [WORKING APP](https://algorand-auction.herokuapp.com/auctions)
The application has a front-end app contained inside the JAR generated from the back-end one and uses the AlgoSDK to access the Algorand blockchain

## Try it

The application can be launched on your machine using the command: 
`java -jar -Dspring.profiles.active=local <path_to_project>/target/algorand-auction-backend-1.0-SNAPSHOT.jar`

A local configuration is set to launch and test locally the application before sending it live

Before you launch it, you must create a new file in *algorand-auction-backend/src/main/resources/* folder called *algorand.properties* which the following properties: 
```text
purestake.main.url=<url_of_the_algorand_test_net>
purestake.main.port=<value_of_the_algorand_test_port>
purestake.main.api_key=<api_key_to_connect_the_infrastructure>
purestake.indexer.url=<url_of_the_algorand_indexer_net>
purestake.indexer.port=<value_of_the_algorand_indexer_port>
```

## Architecture

### Back-End module

The back-end module is a Java 11 application developed on SpringBoot framework 2.4.4 version. 
The connection to the Algorand blockchain is done via the Java version of the Algorand SDK (Software Development Kit) connected to the PureStake network, a highly performing infrastructure allowing developers to connect to the Algorand network ([PureStake API](https://www.purestake.com/)).

Every class of the application is Unit tested to ease the development of new features and be sure that every change does not break the code. The database is a PostgreSQL DB for live application and H2 for local application

The project architecture follows the concepts of the Hexagonal Architecture (for more information: [Hexagonal Architecture](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software))) where in *com.algorand.auction.model* and *com.algorand.auction.usecase* package there is the domain layer, and the repository interfaces that will be implemented in the used frameworks. 
All the files related to the Algorand blockchain are inside the package *com.algorand.auction.blockchain* so that in future other blockchain technologies could be easily plugged in. Moreover, a scheduled job runs every minute to check for finished auctions and execute the Algorand transactions

### Algorand Blockchain features

These are the following features the application takes advantage of:

* Account: every user in the application has an associated Algorand account to receive and send Algos
* Transaction: every executed transaction is a Payment transaction
* Indexer: when logging, the user receives the history of the executed transactions
* Asset (in progress): the application creates fidelity points as Algorand Blockchain assets that are send to the users





