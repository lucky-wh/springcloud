package cn.luckywh.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {
    //    =======================================服务降级============================================================
    //正常访问肯定ok的方法
    @Override
    public String paymentInfo_Ok(Integer id) {
        return "线程池： " + Thread.currentThread().getName() + " paymentInfo_ok,id " + id + "\t" + "哈哈哈哈";
    }


    @Override
    //设置服务的降级，此方法出错后的替代方法
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            //设置自身超时调用时间的峰值为3秒，峰值内可以正常运行，超过了需要有兜底的方法处理，服务降级fallback
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String paymentInfo_TimeOut(Integer id) {
        //线程睡三秒钟
        int timeNumber = 3;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池： " + Thread.currentThread().getName() + " paymentInfo_TimeOut,id " + id + "\t" + "呜呜呜,耗时三秒";
    }

    public String paymentInfo_TimeOutHandler(Integer id) {
        return "线程池： " + Thread.currentThread().getName() + " paymentInfo_TimeOutHandler系统繁忙，请稍后再试,id " + id + "\t" + "呜呜呜,耗时三秒";
    }

    //    =======================================服务熔断============================================================
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),                      //开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),         //请求总数阈值（默认20）
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),   //休眠时间窗口期（休眠多久进入半开模式（单位毫秒，默认5秒））
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),       //请求次数的错误率达到多少跳闸（百分率%，默认50%）
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("****id 不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName() + "\t" + "调用成功，流水号：" + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "id 不能为负数,请稍后再试， id: " + id;
    }

}
