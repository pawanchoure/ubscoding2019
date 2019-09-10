package com.pawan.choure.ubs.service;

import com.pawan.choure.ubs.model.TradeBook;

/**
 * TradeBookConsumer: TradeBook Consumer Interface
 */
public interface TradeBookConsumer {
    /**
     * consumeTradeBook : This method will consume the TradeBook
     * @param tradeBook
     */
    void consumeTradeBook(TradeBook tradeBook);

    /**
     * publishAggregatedRecords : Dummy Method to publish Aggregated Record-- We are just printing the object
     */
    void publishAggregatedRecords();
}
