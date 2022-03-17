package com.guzuro.Daos.ProductDao;

import com.guzuro.Daos.Config.WarehouseDao.Warehouse;

public class Product {
    private long sku;
    private String category;
    private String name;
    private String description;
    private double price;
    private double price_old;
    private String currency;
    private double quantity;
    private String unit;
    private String photo_main;
    private Warehouse warehouse;
    private int company_id;
    private String[] photos;


    public Product() {
    }

    public Product(String category, String name, String description, double price, double price_old, String currency, double quantity, String unit, String photo_main, Warehouse warehouse, int company_id) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.price_old = price_old;
        this.currency = currency;
        this.quantity = quantity;
        this.unit = unit;
        this.photo_main = photo_main;
        this.warehouse = warehouse;
        this.company_id = company_id;
    }

    public Product(long sku, String category, String name, String description, double price, double price_old, String currency, double quantity, String unit, String photo_main, Warehouse warehouse) {
        this.sku = sku;
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.price_old = price_old;
        this.currency = currency;
        this.quantity = quantity;
        this.unit = unit;
        this.photo_main = photo_main;
        this.warehouse = warehouse;
    }

    public Product(long sku, String category, String name, String description, double price, double price_old, String currency, double quantity, String unit, String photo_main, Warehouse warehouse, String[] photos) {
        this.sku = sku;
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.price_old = price_old;
        this.currency = currency;
        this.quantity = quantity;
        this.unit = unit;
        this.photo_main = photo_main;
        this.warehouse = warehouse;
        this.photos = photos;
    }

    public long getSku() {
        return sku;
    }

    public void setSku(long sku) {
        this.sku = sku;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice_old() {
        return price_old;
    }

    public void setPrice_old(double price_old) {
        this.price_old = price_old;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPhoto_main() {
        return photo_main;
    }

    public void setPhoto_main(String photo_main) {
        this.photo_main = photo_main;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public String[] getPhotos() {
        return photos;
    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }
}
