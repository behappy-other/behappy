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

package org.xiaowu.behappy.common.core.util;


import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.xiaowu.behappy.common.core.constant.CommonConstants;
import org.xiaowu.behappy.common.core.enums.CommonBizCode;

import java.io.Serializable;

/**
 * @author xiaowu
 * 响应信息主体
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Slf4j
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * response返回码
     */
    @Setter
    @Getter
    private int code;

    /**
     * response返回信息(错误信息)
     */
    @Setter
    @Getter
    private String msg;

    /**
     * response返回体
     */
    @Setter
    @Getter
    private T data;

    public static <T> Response<T> ok() {
        return Response.restResult(null, CommonConstants.SUCCESS, null);
    }

    public static <T> Response<T> ok(T data) {
        return Response.restResult(data, CommonConstants.SUCCESS, null);
    }

    public static <T> Response<T> ok(T data, String msg) {
        return Response.restResult(data, CommonConstants.SUCCESS, msg);
    }

    public static <T> Response<T> failed() {
        return Response.restResult(null, CommonConstants.FAIL, null);
    }

    public static <T> Response<T> failed(String msg) {
        return Response.restResult(null, CommonConstants.FAIL, msg);
    }

    public static <T> Response<T> failed(T data) {
        return Response.restResult(data, CommonConstants.FAIL, null);
    }

    public static <T> Response<T> failed(T data, String msg) {
        return Response.restResult(data, CommonConstants.FAIL, msg);
    }

    public static <T> Response<T> failed(int code, String msg) {
        return Response.restResult(null, code, msg);
    }

    public static <T> Response<T> failed(Throwable throwable) {
        return Response.restResult(null, CommonConstants.FAIL, throwable.getMessage());
    }

    /**
     * 未登录返回结果
     */
    public static <T> Response<T> unauthorized(T data) {
        return Response.restResult(null, CommonBizCode.UNAUTHORIZED.getCode(), CommonBizCode.UNAUTHORIZED.getMsg());
    }

    /**
     * 未授权返回结果
     */
    public static <T> Response<T> forbidden(T data) {
        return Response.restResult(null, CommonBizCode.FORBIDDEN.getCode(), CommonBizCode.FORBIDDEN.getMsg());
    }

    private static <T> Response<T> restResult(T data, int code, String msg) {
        Response<T> apiResult = new Response<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

}
