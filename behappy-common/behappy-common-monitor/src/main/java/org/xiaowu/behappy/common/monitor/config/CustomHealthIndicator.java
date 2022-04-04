//package org.xiaowu.behappy.common.monitor.config;
//import org.springframework.boot.actuate.health.AbstractHealthIndicator;
//import org.springframework.boot.actuate.health.Health;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class CustomHealthIndicator extends AbstractHealthIndicator {
//
//    private static Integer a = 2;
//
//    @Override
//    protected void doHealthCheck(Health.Builder builder) throws Exception {
//        // 使用 builder来创建健康状态信息
//        // 如果throw exception，那么status就会被置为DOWN，异常信息会被记录下来
//        Map<String, String> details = new HashMap<String, String>(16);
//        if (a%2==0){
//            details.put("adapter_status","0");
//        }else {
//            details.put("adapter_status","1");
//        }
//        a++;
//        builder.up().withDetails(details).build();
//    }
//}