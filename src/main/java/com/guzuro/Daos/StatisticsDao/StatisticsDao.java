package com.guzuro.Daos.StatisticsDao;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public interface StatisticsDao {
    CompletableFuture<Boolean> addSpendRecord(Statistics record, int company_id);

    CompletableFuture<Boolean> addIncomeRecord(Statistics record, int company_id);

    CompletableFuture<ArrayList<Statistics>> getStat(int company_id);
}
