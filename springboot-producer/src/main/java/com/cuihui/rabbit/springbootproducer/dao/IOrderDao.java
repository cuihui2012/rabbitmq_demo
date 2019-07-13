package com.cuihui.rabbit.springbootproducer.dao;

import java.sql.SQLException;
import java.util.Map;

//@Mapper //引导文件注解@MapperScan可替代
public interface IOrderDao {
    public void addOrder(Map<String, Object> paramMap) throws SQLException;
}
