package com.cuihui.rabbit.springbootproducer.controller;

import com.cuihui.rabbit.springbootproducer.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @RequestMapping(value = "/addOrder",method = RequestMethod.POST)
    public String addUser(@RequestBody Map<String,Object> paramMap){
        return orderService.addOrder(paramMap);
    }
}