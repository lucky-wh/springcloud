package cn.luckywh.service;

import cn.luckywh.pojo.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {
    int add(Payment payment);

    Payment getPaymentById(@Param("id") long id);
}
