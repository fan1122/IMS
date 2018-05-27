package com.liziczh.ims.service;

import com.liziczh.ims.domain.Product;

public interface IStockOutService {
    public Product checkProduct(int id, int count);
    public void stockOut(int id, int count, String register, String recordType);
}