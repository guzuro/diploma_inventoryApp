package com.guzuro.Daos.SaleDocDao;

import com.guzuro.Dto.SaleDocumentDto;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public interface SaleDocDao {
    CompletableFuture<SaleDoc> addSaleDoc(SaleDoc incomeDoc, int companyId);

    CompletableFuture<CopyOnWriteArrayList<SaleDoc>> getIncomeDocBySupplier(int supplier_id);

    CompletableFuture<Boolean> deleteSaleDoc(int incomeDoc_id);

    CompletableFuture<SaleDoc> updateSaleDoc(SaleDoc incomeDoc);

    CompletableFuture<CopyOnWriteArrayList<SaleDocumentDto>> getSaleDocs(int company_id);

    CompletableFuture<SaleDoc> getSaleDoc(int saleDocId);

    CompletableFuture<Boolean> paySaleDoc(int saleDocId, int company_id);
}