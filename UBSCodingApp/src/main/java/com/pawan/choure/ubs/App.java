package com.pawan.choure.ubs;

import com.pawan.choure.ubs.model.TradeBook;
import com.pawan.choure.ubs.serviceimpl.TradeBookConsumerImpl;
import com.pawan.choure.ubs.serviceimpl.TradeBookProducerImpl;
import com.pawan.choure.ubs.utility.BUYSELL;
import com.pawan.choure.ubs.utility.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;


public class App {
    private static final Logger LOG = LoggerFactory.getLogger(App.class);
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("UBSH2DB");


    public static void main(String[] args) {
        LOG.info("App:Main UBS Trade Processing App Started");

        //Define the Number of Producer and Consumer
        int tradeBookProducer = 2;
        int tradeBookConsumer = 1;

        //Using Transfer Queue for Message Transfer
        TransferQueue<TradeBook> transferQueueTradeBook = new LinkedTransferQueue<>();

        //Creating Fixed Thread Pool
        ExecutorService executorServiceTradeProducer = Executors.newFixedThreadPool(tradeBookProducer);
        ExecutorService executorServiceTradeConsumer = Executors.newFixedThreadPool(tradeBookConsumer);

        //Prepare the Trades to be Inserted into H2 Database
        Utility.create(1, LocalDate.of(2019, 11, 10), "UBS", "APPL", new BigDecimal(102.13), BUYSELL.BUY, 100, ENTITY_MANAGER_FACTORY.createEntityManager());
        Utility.create(2, LocalDate.of(2019, 11, 10), "UBS", "IBM",  new BigDecimal(150), BUYSELL.BUY, 150, ENTITY_MANAGER_FACTORY.createEntityManager());
        Utility.create(3, LocalDate.of(2019, 11, 10), "UBS", "MST",  new BigDecimal(160), BUYSELL.BUY, 200, ENTITY_MANAGER_FACTORY.createEntityManager());
        Utility.create(4, LocalDate.of(2019, 11, 10), "UBS", "GOOG",  new BigDecimal(180), BUYSELL.BUY, 250, ENTITY_MANAGER_FACTORY.createEntityManager());
        Utility.create(5, LocalDate.of(2019, 11, 10), "UBS", "AMZ",  new BigDecimal(200), BUYSELL.BUY, 100, ENTITY_MANAGER_FACTORY.createEntityManager());
        Utility.create(6, LocalDate.of(2019, 11, 10), "BARC", "APPL",  new BigDecimal(103), BUYSELL.SELL, 200, ENTITY_MANAGER_FACTORY.createEntityManager());
        Utility.create(7, LocalDate.of(2019, 11, 10), "BARC", "IBM",  new BigDecimal(152), BUYSELL.SELL, 250, ENTITY_MANAGER_FACTORY.createEntityManager());
        Utility.create(8, LocalDate.of(2019, 11, 10), "BARC", "MST",  new BigDecimal(165), BUYSELL.SELL, 300, ENTITY_MANAGER_FACTORY.createEntityManager());
        Utility.create(9, LocalDate.of(2019, 11, 10), "BARC", "GOOG",  new BigDecimal(182), BUYSELL.SELL, 350, ENTITY_MANAGER_FACTORY.createEntityManager());
        Utility.create(10, LocalDate.of(2019, 11, 10), "BARC", "GOOG",  new BigDecimal(181), BUYSELL.BUY, 250, ENTITY_MANAGER_FACTORY.createEntityManager());


        // Get All Trades to be Published from H2 Database
        List<TradeBook> tradeBooks = Utility.readAll(ENTITY_MANAGER_FACTORY.createEntityManager());
        if (tradeBooks != null) {
            for (TradeBook tradeBook : tradeBooks) {
                System.out.println(tradeBook);
                //Publish the Trades
                TradeBookProducerImpl tradeBookProducerImpl = new TradeBookProducerImpl(transferQueueTradeBook, tradeBook);
                executorServiceTradeProducer.submit(tradeBookProducerImpl);
            }
        }

        //Consuming the Trades
        executorServiceTradeConsumer.execute(new TradeBookConsumerImpl(transferQueueTradeBook));


        // Let the simulation run for, say, 10 seconds
        try {
            Thread.sleep(10 * 1000);
            executorServiceTradeProducer.shutdown();
            executorServiceTradeConsumer.shutdown();
            ENTITY_MANAGER_FACTORY.close();
            System.exit(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




    }
}
