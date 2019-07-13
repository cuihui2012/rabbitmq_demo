package com.cuihui.rabbit.springbootproducer.producer;

import com.cuihui.rabbit.springbootproducer.constant.Constants;
import com.cuihui.rabbit.springbootproducer.dao.IBrokerMessageLogDao;
import com.cuihui.rabbit.springbootproducer.entity.Order;
import com.cuihui.rabbit.springbootproducer.utils.FastJsonConvertUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Date;

@Component
public class RabbitOrderSender {

    //自动注入RabbitTemplate模板
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private IBrokerMessageLogDao brokerMessageLogDao;

    //回调函数：配置文件中confirm确认
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback(){

        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.out.println("-------------消息响应开始-------------");
            //消息唯一id
            String messageId = correlationData.getId();
            if (ack){
                System.out.println("响应成功...");
                //如果confirm返回成功,则进行更新
                try {
                    brokerMessageLogDao.changeBrokerMessageLogStatus(messageId, Constants.ORDER_SEND_SUCCESS,new Date());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                //失败则进行后续的操作
                System.err.println("异常处理...");
            }
            System.out.println("-------------消息响应结束-------------");
        }
    };

    //发送消息
    public void sendOrder(Order order) throws Exception{
        System.out.println("-------------发送消息开始-------------");
        //设置回调函数
        rabbitTemplate.setConfirmCallback(confirmCallback);

        //消息唯一id
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(order.getMessageId());
        rabbitTemplate.convertAndSend("order-exchange",//exchange
                            "order.abcd",//routingkey绑定exchange和queue
                FastJsonConvertUtil.convertObjectToJSONObject(order),//消息体内容,转换为JsonObject
                                        correlationData);//消息唯一id
        System.out.println("-------------发送消息结束-------------");
    }
}
