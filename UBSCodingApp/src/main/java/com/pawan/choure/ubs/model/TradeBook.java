package com.pawan.choure.ubs.model;

import com.pawan.choure.ubs.utility.BUYSELL;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * TradeBook : Class to Hold TradeBook Object
 */
@Entity
@Table(name = "TRADEBOOK")
public class TradeBook {
    Integer tradeId;
    LocalDate tradeDate;
    String clientCode;
    String stockCode;
    BigDecimal price;
    BUYSELL buySell;
    Integer quantity;
    // This will be Transient since it will be placeholder for calculating the total executed amount price multiply by quantity
    BigDecimal totalExecutedAmount;


    @Id
    @Column(name = "tradeId", nullable = false)
    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    @Basic
    @Column(name = "tradeDate", nullable = false)
    public LocalDate getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    @Basic
    @Column(name = "clientCode", nullable = false)
    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    @Basic
    @Column(name = "stockCode", nullable = false)
    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    @Basic
    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "buySell", nullable = false)
    public BUYSELL getBuySell() {
        return buySell;
    }

    public void setBuySell(BUYSELL buySell) {
        this.buySell = buySell;
    }

    @Basic
    @Column(name = "quantity", nullable = false)
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Transient
    public BigDecimal getTotalExecutedAmount() {
        return totalExecutedAmount;
    }

    public void setTotalExecutedAmount(BigDecimal totalExecutedAmount) {
        this.totalExecutedAmount = totalExecutedAmount;
    }

    @Override
    public String toString() {
        return "TradeBook{" +
                "tradeId=" + tradeId +
                ", tradeDate=" + tradeDate +
                ", clientCode='" + clientCode + '\'' +
                ", stockCode='" + stockCode + '\'' +
                ", price=" + price +
                ", buySell=" + buySell +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradeBook tradeBook = (TradeBook) o;
        return Objects.equals(tradeId, tradeBook.tradeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeId);
    }
}
