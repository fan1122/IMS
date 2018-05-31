package com.liziczh.ims.dao.impl;

import com.liziczh.ims.dao.IRecordDao;
import com.liziczh.ims.domain.Product;
import com.liziczh.ims.domain.Record;
import com.liziczh.ims.tools.DateUtils;
import com.liziczh.ims.tools.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class RecordDaoImpl implements IRecordDao {
    QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
    @Override
    public List<Record> getRecordByDateAndDirName(String beginDate,String endDate, String proName, String recordType,String dirName, int currentPage, int pageSize) throws SQLException {
        List<Record> recordList = null;
        String sql = "select * from \"record\" where (\"date\" between ? and ?) and (\"proName\" like '%' || ? || '%') and \"recordType\" = ?";
        String order = " order by \"date\" desc";
        if("全部".equals(dirName)){
            recordList = queryRunner.query(JDBCUtils.PagenationSql(sql+order,currentPage,pageSize),new BeanListHandler<Record>(Record.class),beginDate,endDate,proName,recordType);
        }else{
            sql += " and \"proName\" in (select \"proName\" from \"product\" where \"dirName\" = ?)";
            recordList = queryRunner.query(JDBCUtils.PagenationSql(sql+order,currentPage,pageSize),new BeanListHandler<Record>(Record.class),beginDate,endDate,proName,recordType,dirName);
        }
        return recordList;
    }

    @Override
    public int getTotalByDateAndDirName(String beginDate, String endDate, String proName,String recordType, String dirName) throws SQLException {
        int total ;
        String sql = "select count(*) from \"record\" where (\"date\" between ? and ?) and (\"proName\" like '%' || ? || '%')  and \"recordType\" = ? ";
        if("全部".equals(dirName)){
            total = Integer.parseInt(queryRunner.query(sql,new ScalarHandler<>(1), beginDate,endDate,proName,recordType).toString().toString());
        }else{
            sql += " and \"proName\" in (select \"proName\" from \"product\" where \"dirName\" = ?)";
            total = Integer.parseInt(queryRunner.query(sql,new ScalarHandler<>(1),beginDate,endDate,proName,recordType,dirName).toString());
        }
        return total;
    }

    @Override
    public List<Record> getAllRecord() throws SQLException {
        String sql = "select * from \"record\" ";
        List<Record> recordList = queryRunner.query(sql,new BeanListHandler<Record>(Record.class));
        return recordList;
    }


    @Override
    public void insertRecord(Product product,int count, String register,String recordType) {
        String sql = "insert into \"record\" values(?,?,?,?,?,?)";
        try {
            queryRunner.update(sql,DateUtils.date2String(new Date()),product.getProId(), product.getProName(),count,register,recordType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateRecord(int proId,String proName) {
        String sql = "update \"record\" set \"proName\" = ? where \"proId\" = ?";
        try {
            queryRunner.update(sql,proName,proId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getTotalCount(String recordType) {
        String sql = "select sum(\"count\") from \"record\" WHERE \"recordType\" = ?";
        int res = 0;
        try {
            res = new Integer(queryRunner.query(sql,new ScalarHandler<>(1),recordType).toString());
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Object[]> getSumCountByDirName(String recordType, String startTime, String endTime) {
        String sql = "select sum(\"record\".\"count\"),\"product\".\"dirName\" " +
                "from \"record\",\"product\" " +
                "WHERE \"record\".\"proId\" = \"product\".\"proId\" " +
                "AND \"recordType\" = ? " +
                "AND \"record\".\"date\" BETWEEN ? AND ? " +
                "GROUP BY \"product\".\"dirName\"";
        try {
            return queryRunner.query(sql,new ArrayListHandler(),recordType,startTime,endTime);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
