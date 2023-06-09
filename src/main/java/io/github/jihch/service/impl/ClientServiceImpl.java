package io.github.jihch.service.impl;

import io.github.jihch.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClientServiceImpl implements ClientService {

    // Ribbon 的负载均衡工具。使用这个对象的时候可以使用 Ribbon 的功能
    @Autowired
    private LoadBalancerClient loadBalancerClient;

//    @Override
//    public String client() {
//        //通过 Ribbon 负载均衡算法之后得到的 URL 实例
//        ServiceInstance si = loadBalancerClient.choose("myproject");
//        System.out.printf("getUri:%s\n", si.getUri());
//        System.out.printf("getHost:%s\n", si.getHost());
//        System.out.printf("getInstanceId:%s\n", si.getInstanceId());
//        System.out.printf("getMetadata:%s\n", si.getMetadata());
//        System.out.printf("getPort:%s\n", si.getPort());
//        System.out.printf("getScheme:%s\n", si.getScheme());
//        System.out.printf("getServiceId:%s\n", si.getServiceId());
//        // 用 Java 代码实现 Http 请求发送及接收响应。
//        /**
//         * Java 中常见的基于 HTTP 协议的工具
//         * HttpClient apache
//         * RestTemplate
//         */
//
//        return null;
//    }

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public String client() {
        String result = restTemplate.getForObject("http://myproject/demo", String.class);
        return result;
    }


}
