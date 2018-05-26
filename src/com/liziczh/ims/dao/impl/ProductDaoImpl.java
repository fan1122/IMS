package com.liziczh.ims.dao.impl;

import com.liziczh.ims.dao.IProductDao;
import com.liziczh.ims.domain.Product;
import com.liziczh.ims.tools.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;


import java.sql.SQLException;
import java.util.List;

public class ProductDaoImpl implements IProductDao {
    private QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
    @Override
    public Product getProductById(int id) throws SQLException {
        String sql = "select * from \"product\" where \"proId\" = ?";
        Product product = queryRunner.query(sql, new BeanHandler<Product>(Product.class), id);
        return product;
    }

    @Override
    public List<Product> getProductByCountAndDirName(String proName, int lowerCount, int upperCount, String dirName) throws SQLException {
        List<Product>  proList = null;
        String sql = "select * from \"product\" where \"proName\" like '%' || ? || '%' and \"count\" between ? and ? ";
        if("全部".equals(dirName)){
            proList = queryRunner.query(sql,new BeanListHandler<Product>(Product.class),proName,lowerCount,upperCount);
        }else{
            sql += " and \"dirName\" = ? ";
            proList = queryRunner.query(sql,new BeanListHandler<Product>(Product.class),proName,lowerCount,upperCount,dirName);
        }
        return proList;
    }

    @Override
    public List<Product> getAllProduct() throws SQLException {
        String sql = "select * from \"product\"";
        List<Product> proList  = queryRunner.query(sql,new BeanListHandler<Product>(Product.class));
        return proList;
    }

    @Override
    public List<Product> getProductCountLess(int count) throws SQLException {
        String sql = "select * from \"product\" where \"count\" < ?";
        List<Product> proList = queryRunner.query(sql,new BeanListHandler<Product>(Product.class),count);
        return proList;
    }

    @Override
    public List<Product> getProductCountOver(int count) throws SQLException {
        String sql = "select * from \"product\" where \"count\" > ?";
        List<Product> proList = queryRunner.query(sql,new BeanListHandler<Product>(Product.class),count);
        return proList;
    }

    @Override
    public void insertProduct(Product product) throws SQLException {
        String sql = "insert into \"product\" values(?,?,?,?,?,?)";
        queryRunner.update(sql,product.getProName(),product.getProId(),product.getDirName(),product.getSupplier(),product.getBrand(),product.getProId());
    }

    @Override
    public void updateProduct(Product product) throws SQLException {
        String sql = "update \"product\" set \"proName\" = ?, \"dirId\" = ?, \"supplier\" = ?, \"brand\" = ?, \"count\" = ? where \"proId\" = ?";
        queryRunner.update(sql,product.getProName(),product.getProId(),product.getDirName(),product.getSupplier(),product.getBrand(),product.getProId());
    }

    @Override
    public void deleteById(int id) throws SQLException {
        String sql = "delete from \"product\" where \"id\" = ?";
        queryRunner.update(sql,id);
    }


}