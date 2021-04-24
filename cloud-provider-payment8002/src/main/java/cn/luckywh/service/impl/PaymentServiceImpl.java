package cn.luckywh.service.impl;

import cn.luckywh.dao.PaymentDao;
import cn.luckywh.pojo.Payment;
import cn.luckywh.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

    @Override
    public int add(Payment payment) {
        return paymentDao.add(payment);
    }

    @Override
    public Payment getPaymentById(long id) {
        return paymentDao.getPaymentById(id);
    }
}
