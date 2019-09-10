package com.pawan.choure.ubs.model;

import com.pawan.choure.ubs.utility.BUYSELL;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Record : This Class will hold the stock related details
 */
public class Record {
String stockCode;
BUYSELL buySell;
BigDecimal notional;
Integer executedQuantity;

    public Record(TradeBook tradeBook) {
             this.setStockCode(tradeBook.getStockCode());
             this.setBuySell(tradeBook.getBuySell());
             this.setExecutedQuantity(tradeBook.getQuantity());
             this.setNotional(tradeBook.getTotalExecutedAmount());
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public BUYSELL getBuySell() {
        return buySell;
    }

    public void setBuySell(BUYSELL buySell) {
        this.buySell = buySell;
    }

    public BigDecimal getNotional() {
        return notional;
    }

    public void setNotional(BigDecimal notional) {
        this.notional = notional;
    }

    public Integer getExecutedQuantity() {
        return executedQuantity;
    }

    public void setExecutedQuantity(Integer executedQuantity) {
        this.executedQuantity = executedQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return Objects.equals(stockCode, record.stockCode) &&
                buySell == record.buySell;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockCode, buySell);
    }

    @Override
    public String toString() {
        return "Record{" +
                "stockCode='" + stockCode + '\'' +
                ", buySell=" + buySell +
                ", notional=" + notional +
                ", executedQuantity=" + executedQuantity +
                '}';
    }
}
