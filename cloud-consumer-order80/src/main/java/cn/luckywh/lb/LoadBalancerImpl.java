package cn.luckywh.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class LoadBalancerImpl implements LoadBalancer {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public final int getAtomicInteger() {
        int current;
        int next;
        do {
            current = this.atomicInteger.get();
            next = current >= 2147483647 ? 0 : current + 1;
        } while (!this.atomicInteger.compareAndSet(current, next));
        System.out.println("*****第几次访问，次数next:" + next);
        return next;
    }


    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        int index = getAtomicInteger() % serviceInstances.size();

        return serviceInstances.get(index);
    }
}
