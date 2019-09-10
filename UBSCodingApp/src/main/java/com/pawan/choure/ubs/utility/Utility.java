package com.pawan.choure.ubs.utility;

import com.pawan.choure.ubs.model.AggregatedRecord;
import com.pawan.choure.ubs.model.TradeBook;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility Class
 */
public final class Utility {
    public static final ConcurrentHashMap<String, AggregatedRecord> aggregatedRecordConcurrentHashMap = new ConcurrentHashMap<>();

    /**
     * Dummy method to create Trade Objects
     *
     * @param tradeId
     * @param date
     * @param clientCode
     * @param stockCode
     * @param price
     * @param buysell
     * @param quantity
     * @param manager
     */
    public static void create(int tradeId, LocalDate date, String clientCode, String stockCode, BigDecimal price, BUYSELL buysell, int quantity, EntityManager manager) {
        // Create an EntityManager
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            TradeBook tradeBook = new TradeBook();
            tradeBook.setTradeId(tradeId);
            tradeBook.setTradeDate(date);
            tradeBook.setClientCode(clientCode);
            tradeBook.setStockCode(stockCode);
            tradeBook.setPrice(price);
            tradeBook.setBuySell(buysell);
            tradeBook.setQuantity(quantity);

            // Save the tradeBook Object
            manager.persist(tradeBook);

            // Commit the transaction
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
        } finally {
            // Close the EntityManager
            manager.close();
        }
    }

    /**
     * Method to read Trade Object from Database
     *
     * @param manager
     * @return
     */
    public static List<TradeBook> readAll(EntityManager manager) {

        List<TradeBook> tradeBookList = null;

        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Get a List of TradeBooks
            tradeBookList = manager.createQuery("SELECT s FROM TradeBook s", TradeBook.class).getResultList();

            // Commit the transaction
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
        } finally {
            // Close the EntityManager
            manager.close();
        }
        return tradeBookList;
    }


}
