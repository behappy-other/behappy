package org.xiaow.behappy.order.listener;

import com.alipay.api.internal.util.AlipaySignature;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xiaow.behappy.order.service.OrderService;
import org.xiaowu.behappy.common.pay.config.AlipayTemplate;
import org.xiaowu.behappy.order.dto.PayAsyncDto;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付宝异步回调通知信息
 * @author xiaowu
 */
@Slf4j
@RestController
@AllArgsConstructor
public class OrderPayedListener {

    private final OrderService orderService;

    private final AlipayTemplate alipayTemplate;

    @SneakyThrows
    @PostMapping("/payed/notify")
    public String handleAliPayed(PayAsyncDto payAsyncDto, HttpServletRequest request) {
        log.info("\n收到支付宝最后的通知数据：\n" + payAsyncDto.toString());
        // 验签
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        // 只要我们收到了支付宝给我们的异步通知 验签成功 我们就要给支付宝返回success
        if (AlipaySignature.rsaCheckV1(params, alipayTemplate.getAlipayPublicKey(), alipayTemplate.getCharset(), alipayTemplate.getSignType())) {
            return orderService.handlePayResult(payAsyncDto);
        }
        log.warn("\n受到恶意验签攻击");
        return "fail";
    }
}
