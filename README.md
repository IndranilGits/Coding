# Coding
Test Codes repository
# User Story
As a Trader i want to be able to monitor stock prices such that when they breach a trigger level orders can be executed automatically
# Implementation
1. Monitors price movements on a single stock
2. Executes a single "buy" instruction for a specified number of lots ,as soon as the price of that stock is below a specified price.
# Libraries used
Java 8
Junit 4
Mockito
Jmock
# Code Implementation Structure
1. Main Program -TradingStrategy
2. We have Price Source- which is an asynchronous process, SourceImpl is the Implementation for the states required by PriceSource.
3. A stream of events is being monitored by the listener.
4. Interested listeners are notified by the processes of PriceSource states- Security and Rate.
5. Price Listener- Events monitoring for trading , if any matched price happens then it triggers a trading.
6. On succesfull trading- a success message is printed.

