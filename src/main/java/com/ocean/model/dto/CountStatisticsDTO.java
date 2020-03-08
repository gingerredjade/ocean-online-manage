package com.ocean.model.dto;

import java.math.BigInteger;

public class CountStatisticsDTO {
    private Integer year;
    private Integer month;
    private BigInteger count;

    public CountStatisticsDTO() {

    }

    public CountStatisticsDTO(Integer year, Integer months, BigInteger count) {
        this.year = year;
        this.month = months;
        this.count = count;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public BigInteger getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CountStatisticsDTO{" +
                "year=" + year +
                ", month=" + month +
                ", count=" + count +
                '}';
    }
}
