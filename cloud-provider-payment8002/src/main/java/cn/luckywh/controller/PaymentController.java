package cn.luckywh.controller;

import cn.luckywh.pojo.CommonResult;
import cn.luckywh.pojo.Payment;
import cn.luckywh.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    //只传给前端CommonResult，不需要前端了解其他的组件
    @PostMapping(value = "/payment/add")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.add(payment);
        log.info("*****插入结果：" + result);
        if (result > 0) {
            return new CommonResult(200, "插入数据成功;端口号:" + serverPort, result);
        } else {
            return new CommonResult(404, "插入数据失败", null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("*****查询结果：" + payment);

        if (payment != null) {
            return new CommonResult(200, "查询成功;端口号:" + serverPort, payment);
        } else {
            return new CommonResult(404, "没有对应记录,查询ID：" + id, null);
        }
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }
}