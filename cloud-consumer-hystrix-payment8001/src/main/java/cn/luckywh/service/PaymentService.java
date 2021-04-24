package cn.luckywh.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public interface PaymentService {
    String paymentInfo_Ok(Integer id);

    String paymentInfo_TimeOut(Integer id);

    String paymentCircuitBreaker(Integer id);
}
