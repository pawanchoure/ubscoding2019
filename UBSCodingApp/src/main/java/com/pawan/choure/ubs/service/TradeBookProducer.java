package com.pawan.choure.ubs.service;

import com.pawan.choure.ubs.model.TradeBook;
/**
 * TradeBookConsumer: TradeBook Producer Interface
 */
public interface TradeBookProducer {
    /**
     * publishTrade : Method to publish Message
     */
    void publishTrade(TradeBook tradeBook);
}
