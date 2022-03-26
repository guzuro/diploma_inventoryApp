package com.guzuro.Daos.ProductDao;

public class Product {
    private long sku;
    private String category;
    private String name;
    private String description;
    private double price_base;
    private double price_sale;
    private String currency;
    private double quantity;
    private String unit;
    private String photo_main;
    private int warehouse_id;
    private int company_id;
    private String photos;


    public Product() {
    }

    public Product(String category, String name, String description, double price_base, double price_sale, String currency, double quantity, String unit, String photo_main, int warehouse_id, int company_id) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.price_base = price_base;
        this.price_sale = price_sale;
        this.currency = currency;
        this.quantity = quantity;
        this.unit = unit;
        this.photo_main = photo_main;
        this.warehouse_id = warehouse_id;
        this.company_id = company_id;
    }

    public Product(long sku, String category, String name, String description, double price_base, double price_sale, String currency, double quantity, String unit, String photo_main, int warehouse_id) {
        this.sku = sku;
        this.category = category;
        this.name = name;
        this.description = description;
        this.price_base = price_base;
        this.price_sale = price_sale;
        this.currency = currency;
        this.quantity = quantity;
        this.unit = unit;
        this.photo_main = photo_main;
        this.warehouse_id = warehouse_id;
    }

    public Product(long sku, String category, String name, String description, double price_base, double price_sale, String currency, double quantity, String unit, String photo_main, int warehouse_id, String photos) {
        this.sku = sku;
        this.category = category;
        this.name = name;
        this.description = description;
        this.price_base = price_base;
        this.price_sale = price_sale;
        this.currency = currency;
        this.quantity = quantity;
        this.unit = unit;
        this.photo_main = photo_main;
        this.warehouse_id = warehouse_id;
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

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public double getPrice_base() {
        return price_base;
    }

    public void setPrice_base(double price_base) {
        this.price_base = price_base;
    }

    public double getPrice_sale() {
        return price_sale;
    }

    public void setPrice_sale(double price_sale) {
        this.price_sale = price_sale;
    }

    public int getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(int warehouse_id) {
        this.warehouse_id = warehouse_id;
    }
}
