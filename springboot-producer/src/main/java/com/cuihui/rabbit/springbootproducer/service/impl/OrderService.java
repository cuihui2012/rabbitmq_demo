package com.cuihui.rabbit.springbootproducer.service.impl;

import com.cuihui.rabbit.springbootproducer.constant.Constants;
import com.cuihui.rabbit.springbootproducer.dao.IBrokerMessageLogDao;
import com.cuihui.rabbit.springbootproducer.dao.IOrderDao;
import com.cuihui.rabbit.springbootproducer.entity.BrokerMessageLog;
import com.cuihui.rabbit.springbootproducer.entity.Order;
import com.cuihui.rabbit.springbootproducer.producer.RabbitOrderSender;
import com.cuihui.rabbit.springbootproducer.service.IOrderService;
import com.cuihui.rabbit.springbootproducer.utils.FastJsonConvertUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderDao orderDao;

    @Autowired
    private IBrokerMessageLogDao brokerMessageLogDao;

    @Autowired
    private RabbitOrderSender rabbitOrderSender;

    @Override
    public String addOrder(Map<String, Object> paramMap) {
        Date orderTime = new Date();

        String flag = "0";
        String uuid = UUID.randomUUID().toString().replaceAll("-","").toUpperCase();
        String messageId = uuid + "$" + System.currentTimeMillis();
        paramMap.put("id", uuid);
        paramMap.put("message_id",messageId);

        Order order = new Order(uuid,(String)paramMap.get("name"),messageId);
        BrokerMessageLog brokerMessageLog = new BrokerMessageLog();
        brokerMessageLog.setMessageId(order.getMessageId());
        brokerMessageLog.setMessage(FastJsonConvertUtil.convertObjectToJSON(order));
        brokerMessageLog.setStatus(Constants.ORDER_SENDING); //设置状态为订单发送中
        brokerMessageLog.setNextRetry(DateUtils.addMinutes(orderTime, Constants.ORDER_TIMEOUT)); //设置下次发送时间为当前时间+超时时间
        brokerMessageLog.setCreateTime(new Date());
        brokerMessageLog.setUpdateTime(new Date());
        try{
            brokerMessageLogDao.addBrokerMessageLog(brokerMessageLog);
            orderDao.addOrder(paramMap);
            rabbitOrderSender.sendOrder(order);
            flag = "1";
        } catch(SQLException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}