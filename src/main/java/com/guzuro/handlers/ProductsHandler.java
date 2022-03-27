package com.guzuro.handlers;

import com.guzuro.Daos.ProductDao.PostgresProductDaoImpl;
import com.guzuro.Daos.ProductDao.Product;
import com.guzuro.Daos.ProductDao.ProductDao;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductsHandler {
    ProductDao dao;

    public ProductsHandler(Vertx vertx) {
        this.dao = new PostgresProductDaoImpl(vertx);
    }

    public void getProducts(RoutingContext context) {
        HttpServerResponse response = context.response();

        this.dao.getAllProducts(context.getBodyAsJson().getInteger("company_id"))
                .thenAccept(products -> {
                    JsonArray productJson = new JsonArray();
                    products.forEach(employee -> productJson.add(JsonObject.mapFrom(employee)));
                    context.response()
                            .setStatusCode(200)
                            .putHeader("content-type", "application/json; charset=UTF-8")
                            .end(productJson.encodePrettily());

                }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500).end(throwable.getMessage());
            return null;
        });
    }

    public void addProduct(RoutingContext context) {
        context.response().setChunked(true);
        HttpServerResponse response = context.response();

        Set<FileUpload> uploads = context.fileUploads();

        Product product = new Product();
        product.setName(context.request().getParam("name"));
        product.setCategory(context.request().getParam("category"));
        product.setCurrency(context.request().getParam("currency"));
        product.setCompany_id(Integer.parseInt(context.request().getParam("company_id")));
        product.setDescription(context.request().getParam("description"));
        product.setPrice_base(Double.parseDouble(context.request().getParam("price_base")));
        product.setPrice_sale(Double.parseDouble(context.request().getParam("price_sale")));
        product.setSku(Long.parseLong(context.request().getParam("sku")));
        product.setUnit(context.request().getParam("unit"));
        product.setQuantity(Double.parseDouble(context.request().getParam("quantity")));
        product.setWarehouse_id(Integer.parseInt(context.request().getParam("warehouse_id")));


        ArrayList<String> photos = new ArrayList<>();

        uploads.forEach(upload -> {
            try {
                File uploadedFile = new File(upload.uploadedFileName());
                uploadedFile.renameTo(new File("webroot/" + upload.fileName()));
                uploadedFile.createNewFile();
                photos.add(upload.fileName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        product.setPhotos(photos);
    }

    public void getProductById(RoutingContext context) {
        System.out.println(context);
    }

    public void removeProduct(RoutingContext context) {
        System.out.println(context);
    }

    public void updateProduct(RoutingContext context) {
        System.out.println(context);
    }
}
