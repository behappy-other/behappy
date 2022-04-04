package org.xiaowu.behappy.common.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.common.core.exception.BeHappyException;

import static org.xiaowu.behappy.common.core.enums.CommonBizCode.JSON_SERIALIZATION_ERROR;

/**
 * @author 小五
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ResponseConvert {

    private final ObjectMapper objectMapper;

    private static void assertResponse(Response response) {
        try {
            assert response.getCode() == 0;
        } catch (Exception e) {
            log.error("assert response error -> code:{},msg:{}", response.getCode(), response.getMsg());
            throw new BeHappyException(response.getCode(), response.getMsg());
        }
    }

    public <T> T convert(Response response, TypeReference<T> type) {
        try {
            ResponseConvert.assertResponse(response);
            return objectMapper.readValue(objectMapper.writeValueAsString(response.getData()), type);
        } catch (JsonProcessingException e) {
            log.error("Response转换：序列化错误");
            throw new BeHappyException(JSON_SERIALIZATION_ERROR.getCode(), JSON_SERIALIZATION_ERROR.getMsg());
        } catch (BeHappyException e) {
            throw e;
        } catch (Exception e) {
            log.error("Response转换：未知错误");
            throw e;
        }
    }
}
