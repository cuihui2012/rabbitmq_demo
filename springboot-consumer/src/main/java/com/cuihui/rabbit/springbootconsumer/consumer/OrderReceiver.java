package com.cuihui.rabbit.springbootconsumer.consumer;

import com.alibaba.fastjson.JSONObject;
import com.cuihui.rabbit.springbootconsumer.entity.Order;
import com.cuihui.rabbit.springbootconsumer.utils.FastJsonConvertUtil;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderReceiver {

    //启动客户端,此处监听配置会在RabbitMQ服务器创建
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}",
                            durable = "${spring.rabbitmq.listener.order.queue.durable}"),
                    exchange = @Exchange(name = "${spring.rabbitmq.listener.order.exchange.name}",
                            durable = "${spring.rabbitmq.listener.order.exchange.durable}",
                            type = "${spring.rabbitmq.listener.order.exchange.type}",
                            ignoreDeclarationExceptions = "${spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions}"),
                    key = "${spring.rabbitmq.listener.order.key}"  //*只模糊匹配一级,#可以模糊匹配多级,例：order.asd.sdf
            )
    )
    @RabbitHandler
    public void onOrderMessage(@Payload JSONObject object,
                               @Headers Map<String,Object> headers,
                               Channel channel) throws Exception{
        //消费者操作
        System.out.println("-------------消费消息开始-------------");
        Order order = FastJsonConvertUtil.convertJSONToObject(object,Order.class);
        System.out.println("订单id----->" + order.getId());

        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        //手动签收(false-不支持批量签收)
        channel.basicAck(deliveryTag,false);
        System.out.println("-------------消费消息结束-------------");
    }
}