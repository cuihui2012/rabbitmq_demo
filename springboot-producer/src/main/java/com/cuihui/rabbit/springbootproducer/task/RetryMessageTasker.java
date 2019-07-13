package com.cuihui.rabbit.springbootproducer.task;

import com.cuihui.rabbit.springbootproducer.constant.Constants;
import com.cuihui.rabbit.springbootproducer.dao.IBrokerMessageLogDao;
import com.cuihui.rabbit.springbootproducer.entity.BrokerMessageLog;
import com.cuihui.rabbit.springbootproducer.entity.Order;
import com.cuihui.rabbit.springbootproducer.producer.RabbitOrderSender;
import com.cuihui.rabbit.springbootproducer.utils.FastJsonConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Component
public class RetryMessageTasker {

    @Autowired
    private RabbitOrderSender rabbitOrderSender;

    @Autowired
    private IBrokerMessageLogDao brokerMessageLogDao;

    @Scheduled(initialDelay = 3000, fixedDelay = 10000) //定时任务：项目启动三秒，每隔10秒调用
    public void reSend(){
        System.out.println("--------定时任务开始---------");
        //查询状态为0和超时的消息
        List<BrokerMessageLog> messageLogList = brokerMessageLogDao.queryStatus0AndTimeoutMessage();
        try {
            for (BrokerMessageLog messageLog : messageLogList) {
                if (messageLog.getTryCount() >= 3){
                    //重复投递了三次以上，更新消息状态为失败
                    brokerMessageLogDao.changeBrokerMessageLogStatus(messageLog.getMessageId(), Constants.ORDER_SEND_FALIURE,new Date());
                } else {
                    //重投次数+1
                    brokerMessageLogDao.updateTryCount(messageLog.getMessageId(),new Date());
                    Order order = FastJsonConvertUtil.convertJSONToObject(messageLog.getMessage(),Order.class);
                    rabbitOrderSender.sendOrder(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------定时任务结束---------");
    }
}
