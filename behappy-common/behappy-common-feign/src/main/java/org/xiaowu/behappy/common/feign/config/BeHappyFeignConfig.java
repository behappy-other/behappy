package org.xiaowu.behappy.common.feign.config;

/**
 * @author xiaowu
 */
//@Configuration(proxyBeanMethods = false)
public class BeHappyFeignConfig {

    /**
     * 解决远程调用时丢失cookie问题
     */
    //@Bean
    //public RequestInterceptor requestInterceptor() {
    //    return new RequestInterceptor() {
    //        @Override
    //        public void apply(RequestTemplate requestTemplate) {
    //            // 1、RequestContextHolder拿到刚进来的这个请求
    //            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    //            if (attributes != null) {
    //                // 获取到老请求中的Cookie
    //                HttpServletRequest request = attributes.getRequest();
    //                String cookie = request.getHeader("Cookie");
    //                // 2、同步请求头信息：cookie
    //                // 为新请求设置新cookie,同步老请求cookie
    //                requestTemplate.header("Cookie", cookie);
    //            }
    //        }
    //    };
    //}

    /**
     * 由于直接使用了Feign Client，因此就不再使用RestTemplate进行处理
     */
    //@Bean
    //@LoadBalanced
    //@ConditionalOnMissingBean
    //public RestTemplate restTemplate() {
    //    return new RestTemplate();
    //}

}