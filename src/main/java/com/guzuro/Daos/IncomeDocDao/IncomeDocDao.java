package com.guzuro.Daos.IncomeDocDao;

import com.guzuro.Daos.OrderDao.Order;
import com.guzuro.Dto.IncomeDocumentDto;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public interface IncomeDocDao {
    CompletableFuture<IncomeDoc> addIncomeDoc(IncomeDoc incomeDoc, int companyId);

    CompletableFuture<CopyOnWriteArrayList<IncomeDoc>> getIncomeDocBySupplier(int supplier_id);

    CompletableFuture<Boolean> deleteIncomeDoc(int incomeDoc_id);

    CompletableFuture<IncomeDoc> updateIncomeDoc(IncomeDoc incomeDoc);

    CompletableFuture<CopyOnWriteArrayList<IncomeDocumentDto>> getIncomeDocs(int company_id);

    CompletableFuture<IncomeDoc> getIncomeDoc(int incomeDocId);

}