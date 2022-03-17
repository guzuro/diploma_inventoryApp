package com.guzuro.Daos.Config.Category;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public interface CategoryDao {
    CompletableFuture<Category> addCategory(Category category, int company_id);

    CompletableFuture<Category> updateCategory(Category category);

    CompletableFuture<Boolean> deleteCategory(int category_id);

    CompletableFuture<CopyOnWriteArrayList<Category>> getCategories(int company_id);
}