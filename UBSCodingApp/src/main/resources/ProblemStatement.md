# Overview

Consider a system which handles trades. The system is restarted every morning before the market opens. After the market opens, trades are received from a trade source. Each trade record contains a trade date, a trade Id, a client code, a stock code, a side (buy/sell), executed price and quantity. An aggregation is done whenever a new trade is received in the system to get the total executed amount (executed amount = price * executed quantity) and executed quantity for each client code, stock code and side. The aggregated record having a client code, a stock code and a side, a notional and an executed quantity is called a block. After the market closes, all the blocks derived in the system are delivered to downstream system for further processing.

# Objectives

The aim is to design and implement this system in Java that takes in trades from a trade source and publishes real time block information. 

The publishing must be done at an API level. No message brokers are expected. You will have to provide implementation for two subscribers of this publisher:

1. A console program that keeps printing out the block record whenever there is a new block received or updated block to mimic a GUI monitoring all blocks real time.
2. A text file printer that runs on demand and prints all the blocks for the day to mimic the process that sends the blocks to downstream after the market is close.

The objectives are intentionally vague, so you will need to make some assumptions about the requirements. These must be documented in your response.

# Additional Notes

You are not expected to consume real trade data or to support real stocks or clients. On the contrary, you are required to implement a mock trade source that publishes trades randomly with the following rules.

1.	A trade comes in every 100 milliseconds - 500 milliseconds randomly.
2.	You can define your own set of stocks with each having a closing price and a total executed quantity from previous trading date. Trades are evenly distributed across different stocks. 
3.	You can define your own set of clients. Trades are evenly distributed across different clients. 
4.	The price movement (whether it's up or down) is bounded by 1% of last traded price or previous day closing price and follows a uniform distribution.
5.	You can make your own choice to generate the executed quantity of each trade.

To begin with, you need to design a small database of stocks and clients. You should use H2 database for implementation. It is open source and does not require installation on local machine. This will allow us to test your submission easily.

You can assume each stock in this database will have a stock code and a previous day closing price and each client will have only a client code.

You are not expected to design real-time communication channels. Feel free to assume that the mock trade source described above sits in the same process space as your program. But you are encouraged to design your program with parallelization in mind to scale up the throughput.
