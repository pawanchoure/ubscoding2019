package com.pawan.choure.ubs.serviceimpl;

import com.pawan.choure.ubs.model.TradeBook;
import com.pawan.choure.ubs.service.TradeBookProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TradeBookProducerImpl implements Runnable, TradeBookProducer {
    private static final Logger LOG = LoggerFactory.getLogger(TradeBookProducer.class);
    protected TransferQueue<TradeBook> tradeBookSharedQueue;
    private TradeBook tradeBook;
    public AtomicInteger numberOfProducedMessagesByTopBook
            = new AtomicInteger();

    /**
     * TradeBookProducerImpl : Constructor for creating TradeBookProducerImpl
     *
     * @param queue
     * @param tradeBook
     */
    public TradeBookProducerImpl(TransferQueue<TradeBook> queue, TradeBook tradeBook) {
        this.tradeBookSharedQueue = queue;
        this.tradeBook = tradeBook;
    }

    /**
     * publishTrade : Method to publish Message
     *
     * @param tradeBook
     */
    @Override
    public void publishTrade(TradeBook tradeBook) {
        try {
            // Put the TradeObject into Shared Queue
            LOG.debug("TradeBookProducerImpl:publishTrade Started");
            tradeBookSharedQueue.put(tradeBook);
            numberOfProducedMessagesByTopBook.incrementAndGet();
            LOG.debug("TradeBookProducerImpl:publishTrade Produced - Queue Size is now = " + tradeBookSharedQueue.size());

        } catch (InterruptedException e) {
            LOG.debug("TradeBookProducerImpl:publishTrade Issue with publish Trade " + e.getMessage());
            e.printStackTrace();
        }
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
        LOG.debug("TradeBookProducerImpl:run Starting Publishing Trade ");
        try {
            publishTrade(tradeBook);
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }



        LOG.debug("TradeBookProducerImpl:run Finished Publishing Trade ");
    }


}
