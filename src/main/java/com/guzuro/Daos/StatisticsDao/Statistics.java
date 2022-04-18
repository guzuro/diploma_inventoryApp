package com.guzuro.Daos.StatisticsDao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

public class Statistics {

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;
    private double income_sum;
    private double spend_sum;

    public Statistics(){}

    public Statistics(LocalDate date, double income_sum, double spend_sum) {
        this.date = date;
        this.income_sum = income_sum;
        this.spend_sum = spend_sum;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getIncome_sum() {
        return income_sum;
    }

    public void setIncome_sum(double income_sum) {
        this.income_sum = income_sum;
    }

    public double getSpend_sum() {
        return spend_sum;
    }

    public void setSpend_sum(double spend_sum) {
        this.spend_sum = spend_sum;
    }
}