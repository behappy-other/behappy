/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.xiaowu.behappy.gateway.exception;

import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import org.xiaowu.behappy.common.core.util.Response;
import reactor.core.publisher.Mono;

import static org.xiaowu.behappy.common.core.enums.CommonBizCode.*;

/**
 * 网关异常通用处理器，只作用在webflux 环境下 , 优先级低于 {@link ResponseStatusExceptionHandler} 执行
 *
 * @author xiaowu
 */
@Slf4j
@Order(-1)
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        // header set
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if (ex instanceof ResponseStatusException) {
            response.setStatusCode(((ResponseStatusException) ex).getStatus());
        }

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            try {
                log.warn("Error Spring Cloud Gateway : {}", ex.getMessage());
                if (ex instanceof FlowException) {
                    return bufferFactory.wrap(objectMapper.writeValueAsBytes(Response.failed(FLOW_EXCEPTION.getCode(), FLOW_EXCEPTION.getMsg())));
                } else if (ex instanceof DegradeException) {
                    return bufferFactory.wrap(objectMapper.writeValueAsBytes(Response.failed(DEGRADE_EXCEPTION.getCode(), DEGRADE_EXCEPTION.getMsg())));
                } else if (ex instanceof ParamFlowException) {
                    return bufferFactory.wrap(objectMapper.writeValueAsBytes(Response.failed(PARAM_FLOW_EXCEPTION.getCode(), PARAM_FLOW_EXCEPTION.getMsg())));
                } else if (ex instanceof SystemBlockException) {
                    return bufferFactory.wrap(objectMapper.writeValueAsBytes(Response.failed(SYSTEM_BLOCK_EXCEPTION.getCode(), SYSTEM_BLOCK_EXCEPTION.getMsg())));
                } else if (ex instanceof AuthorityException) {
                    return bufferFactory.wrap(objectMapper.writeValueAsBytes(Response.failed(AUTHORITY_EXCEPTION.getCode(), AUTHORITY_EXCEPTION.getMsg())));
                }
                return bufferFactory.wrap(objectMapper.writeValueAsBytes(Response.failed()));
            } catch (JsonProcessingException e) {
                log.error("Error writing response", ex);
                return bufferFactory.wrap(new byte[0]);
            }
        }));
    }

}
