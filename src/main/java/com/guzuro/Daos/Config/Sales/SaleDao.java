package com.guzuro.Daos.Config.Sales;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public interface SaleDao {
    CompletableFuture<Sale> addSale(Sale company, int company_id);

    CompletableFuture<Sale> updateSale(Sale sale);

    CompletableFuture<Boolean> deleteSale(int sale_id);

    CompletableFuture<CopyOnWriteArrayList<Sale>> getSales(int company_id);
}