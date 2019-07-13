package com.cuihui.rabbit.springbootproducer.dao;

import com.cuihui.rabbit.springbootproducer.entity.BrokerMessageLog;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IBrokerMessageLogDao {
    public void changeBrokerMessageLogStatus(@Param("messageId") String messageId, @Param("status")String status, @Param("updateTime")Date updateTime) throws SQLException;
    public void addBrokerMessageLog(@Param("messageLog") BrokerMessageLog brokerMessageLog) throws SQLException;
    public List<BrokerMessageLog> queryStatus0AndTimeoutMessage();
    public void updateTryCount(@Param("messageId") String messageId, @Param("updateTime") Date updateTime) throws SQLException;
}
