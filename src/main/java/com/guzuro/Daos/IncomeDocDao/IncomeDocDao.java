package com.guzuro.Daos.IncomeDocDao;

import com.guzuro.Daos.OrderDao.Order;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public interface IncomeDocDao {
    CompletableFuture<IncomeDoc> addIncomeDoc(IncomeDoc incomeDoc, int company_id);

    CompletableFuture<CopyOnWriteArrayList<IncomeDoc>> getIncomeDocBySupplier(int supplier_id);

    CompletableFuture<Boolean> deleteIncomeDoc(int incomeDoc_id);

    CompletableFuture<IncomeDoc> updateIncomeDoc(IncomeDoc incomeDoc);

    CompletableFuture<CopyOnWriteArrayList<IncomeDoc>> getIncomeDocs(int company_id);
}