package com.pawan.choure.ubs.serviceimpl;

import com.pawan.choure.ubs.model.AggregatedRecord;
import com.pawan.choure.ubs.model.Record;
import com.pawan.choure.ubs.model.TradeBook;
import com.pawan.choure.ubs.service.TradeBookConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static com.pawan.choure.ubs.utility.Utility.aggregatedRecordConcurrentHashMap;

public class TradeBookConsumerImpl implements Runnable, TradeBookConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(TradeBookConsumerImpl.class);
    protected TransferQueue<TradeBook> tradeBookTransferQueue;
    private TradeBook tradeBook;
    public AtomicInteger numberOfConsumedMessagesTopBook
            = new AtomicInteger();

    public TradeBookConsumerImpl(TransferQueue<TradeBook> sharedQueue) {
        this.tradeBookTransferQueue = sharedQueue;
    }


    /**
     * consumeTradeBook : Consume the TradeBook
     *
     * @param tradeBook
     */
    @Override
    public void consumeTradeBook(TradeBook tradeBook) {
        LOG.debug("TradeBookConsumerImpl : consumeTradeBook  Started for Client Code " + tradeBook.getClientCode());

        //Enrich totalExecutedAmount
        if(tradeBook.getPrice()!=null && tradeBook.getQuantity()!=null){
            tradeBook.setTotalExecutedAmount(calculateTotalExecutedAmount(tradeBook.getQuantity(),tradeBook.getPrice()));
            LOG.debug("TradeBookConsumerImpl:consumeTradeBook total executed price for stock"+tradeBook.getStockCode()+" having price "+tradeBook.getPrice() +" and quantity "+tradeBook.getQuantity()+ " is "+tradeBook.getTotalExecutedAmount());
        }
        if (aggregatedRecordConcurrentHashMap.containsKey(tradeBook.getClientCode())) {
            AggregatedRecord existingObject = aggregatedRecordConcurrentHashMap.get(tradeBook.getClientCode());
            List<Record> recordList = existingObject.getRecordList();
            Record newRecord = new Record(tradeBook);
            recordList.add(newRecord);
        } else {
            aggregatedRecordConcurrentHashMap.put(tradeBook.getClientCode(), new AggregatedRecord(tradeBook));

        }
        LOG.debug("TradeBookConsumerImpl:consumeTradeBook Aggregated Object Map Size "+aggregatedRecordConcurrentHashMap.size());
        LOG.debug("TradeBookConsumerImpl :consumeTradeBook  Completed for Client Code" + tradeBook.getClientCode());
    }

    /**
     * publishAggregatedRecords : Dummy Method to publish Aggregated Record-- We are just printing the object
     */
    @Override
    public void publishAggregatedRecords() {
        LOG.debug("TradeBookConsumerImpl:publishAggregatedRecords"+ aggregatedRecordConcurrentHashMap.toString());
    }

    /**
     * calculateTotalExecutedAmount : Calculate Executed Amount based on quantity and price
     * @param quantity
     * @param price
     * @return
     */
    private BigDecimal calculateTotalExecutedAmount(Integer quantity, BigDecimal price) {
        return price.multiply(new BigDecimal(quantity));
    }


    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        LOG.info("TradeBookConsumerImpl : run Started");
        while (true) {
            try {
                //Get the Trade Object from Queue
                tradeBook = tradeBookTransferQueue.take();
                if(tradeBook!=null){
                    numberOfConsumedMessagesTopBook.incrementAndGet();
                    LOG.info("TradeBookConsumerImpl : run " + tradeBook.getTradeId());
                    consumeTradeBook(tradeBook);
                    LOG.debug(aggregatedRecordConcurrentHashMap.toString());

                    //Dummy Publish Method
                    publishAggregatedRecords();
                }

                LOG.info("TradeBookConsumerImpl : run  Completed");
            } catch (InterruptedException ex) {
                LOG.debug(ex.getMessage());
            }
        }
    }
}
