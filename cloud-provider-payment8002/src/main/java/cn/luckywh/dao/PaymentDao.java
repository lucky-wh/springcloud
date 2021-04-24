package cn.luckywh.dao;

import cn.luckywh.pojo.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentDao {

    int add(Payment payment);

    Payment getPaymentById(@Param("id") long id);
}
