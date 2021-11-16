package com.guzuro.ProductDao;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public interface ProductDao {

    CompletableFuture<Product> createProduct(Product todo);

    CompletableFuture<CopyOnWriteArrayList<Product>> getAllProducts();

    CompletableFuture<Product> updateProduct(Product todo);

    CompletableFuture<Boolean> deleteProduct(Number todoId);
}
