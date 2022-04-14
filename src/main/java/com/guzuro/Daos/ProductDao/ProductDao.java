package com.guzuro.Daos.ProductDao;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public interface ProductDao {

    CompletableFuture<CopyOnWriteArrayList<Product>> getAllProducts(int company_id);

    CompletableFuture<CopyOnWriteArrayList<Product>> getWarehouseProducts(int warehouse_id);

    CompletableFuture<Boolean> removeProduct(long sku);

    CompletableFuture<Product> addProduct(Product product);

    CompletableFuture<Product> updateProduct(Product product);

    CompletableFuture<Product> getProductBySku(long sku);

    CompletableFuture<Boolean> incrementProductQuantity(long sku, int value);
    CompletableFuture<Boolean> decrementProductQuantity(long sku, int value);

}
