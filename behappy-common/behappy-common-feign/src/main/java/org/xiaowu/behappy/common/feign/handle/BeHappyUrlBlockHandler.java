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

package org.xiaowu.behappy.common.feign.handle;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpStatus;
import org.xiaowu.behappy.common.core.util.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static cn.hutool.core.util.CharsetUtil.UTF_8;
import static org.xiaowu.behappy.common.core.enums.CommonBizCode.*;

/**
 * sentinel统一资源限制处理
 *
 * @author xiaowu
 */
@Slf4j
@RequiredArgsConstructor
public class BeHappyUrlBlockHandler implements BlockExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        log.error("sentinel 统一资源限制-资源名称{}", e.getRule().getResource(), e);

        String errMsg = "";
        if (e instanceof FlowException) {
            errMsg = objectMapper.writeValueAsString(Response.failed(FLOW_EXCEPTION.getCode(), FLOW_EXCEPTION.getMsg()));
        } else if (e instanceof DegradeException) {
            errMsg = objectMapper.writeValueAsString(Response.failed(DEGRADE_EXCEPTION.getCode(), DEGRADE_EXCEPTION.getMsg()));
        } else if (e instanceof ParamFlowException) {
            errMsg = objectMapper.writeValueAsString(Response.failed(PARAM_FLOW_EXCEPTION.getCode(), PARAM_FLOW_EXCEPTION.getMsg()));
        } else if (e instanceof SystemBlockException) {
            errMsg = objectMapper.writeValueAsString(Response.failed(SYSTEM_BLOCK_EXCEPTION.getCode(), SYSTEM_BLOCK_EXCEPTION.getMsg()));
        } else if (e instanceof AuthorityException) {
            errMsg = objectMapper.writeValueAsString(Response.failed(AUTHORITY_EXCEPTION.getCode(), AUTHORITY_EXCEPTION.getMsg()));
        }
        response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setCharacterEncoding(UTF_8);
        response.getWriter().print(errMsg);
    }
}
