package com.guzuro.Daos.SupplierDao;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public interface SupplierDao {
    CompletableFuture<Supplier> createSupplier(Supplier supplier, int company_id);

    CompletableFuture<Supplier> getSupplier(int supplier_id);

    CompletableFuture<CopyOnWriteArrayList<Supplier>> getSuppliers(int company_id);

    CompletableFuture<Boolean> deleteSupplier(int supplier_id);

    CompletableFuture<Supplier> updateSupplier(Supplier supplier);
}