package com.liziczh.ims.domain;

import java.util.Objects;

public class Product {
    private int proId;
    private String proName;
    private String dirId;
    private String supplier;
    private String brand;
    private int count;

    public Product() {

    }

    public Product(int proId, String proName, String dirId, String supplier, String brand, int count) {
        this.proId = proId;
        this.proName = proName;
        this.dirId = dirId;
        this.supplier = supplier;
        this.brand = brand;
        this.count = count;
    }

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getDirId() {
        return dirId;
    }

    public void setDirId(String dirId) {
        this.dirId = dirId;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return proId == product.proId &&
                count == product.count &&
                Objects.equals(proName, product.proName) &&
                Objects.equals(dirId, product.dirId) &&
                Objects.equals(supplier, product.supplier) &&
                Objects.equals(brand, product.brand);
    }

    @Override
    public int hashCode() {

        return Objects.hash(proId, proName, dirId, supplier, brand, count);
    }

    @Override
    public String toString() {
        return "Product{" +
                "proId=" + proId +
                ", proName='" + proName + '\'' +
                ", dirId='" + dirId + '\'' +
                ", supplier='" + supplier + '\'' +
                ", brand='" + brand + '\'' +
                ", count=" + count +
                '}';
    }
}
