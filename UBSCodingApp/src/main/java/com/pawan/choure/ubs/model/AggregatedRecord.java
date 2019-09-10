package com.pawan.choure.ubs.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * AggregatedRecord : This class will hold the Aggregated Record Value against each client Code
 */
public class AggregatedRecord {

    //Client Code
    String clientCode;

    //Record List
    List<Record> recordList;

    public AggregatedRecord(TradeBook tradeBook) {
          this.setClientCode(tradeBook.getClientCode());
          this.recordList= new ArrayList<>();
          Record record=new Record(tradeBook);
          this.recordList.add(record);
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public List<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AggregatedRecord that = (AggregatedRecord) o;
        return Objects.equals(clientCode, that.clientCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientCode);
    }

    @Override
    public String toString() {
        return "AggregatedRecord{" +
                "clientCode='" + clientCode + '\'' +
                ", recordList=" + recordList +
                '}';
    }
}
