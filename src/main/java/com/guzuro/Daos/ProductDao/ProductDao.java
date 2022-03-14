package com.guzuro.Daos.ProductDao;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public interface ProductDao {

    CompletableFuture<CopyOnWriteArrayList<Product>> getAllProducts(int company_id);

    CompletableFuture<CopyOnWriteArrayList<Product>> getWarehouseProducts(int warehouse_id);

    CompletableFuture<CopyOnWriteArrayList<Product>> removeProduct(double sku);

    CompletableFuture<Product> addProduct(Product product);

    CompletableFuture<Product> updateProduct(Product product);

}
