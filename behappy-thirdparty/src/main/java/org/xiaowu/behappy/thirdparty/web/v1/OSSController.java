package org.xiaowu.behappy.thirdparty.web.v1;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.thirdparty.config.AliOssProperties;
import org.xiaowu.behappy.thirdparty.vo.PolicyVo;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 生成后端签名
 * @author xiaowu
 */
@RestController
@RequestMapping("/web/v1/oss")
@AllArgsConstructor
public class OSSController {

    private final OSSClient ossClient;

    private final AliOssProperties aliOssProperties;

    /**
     * 生成后端签名
     * @apiNote
     * @author xiaowu
     * @return org.xiaowu.behappy.common.core.util.Response<org.xiaowu.behappy.thirdparty.vo.PolicyVo>
     */
    @GetMapping("/policy")
    public Response<PolicyVo> policy() {
        // host的格式为 bucketname.endpoint
        String host = "https://" + aliOssProperties.getBucket() + "." + aliOssProperties.getEndpoint();

        // callbackUrl为 上传回调服务器的URL，请将下面的IP和Port配置为您自己的真实信息。
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        // 用户上传文件时指定的前缀。
        String dir = format + "/";

        PolicyVo policyVo = new PolicyVo();
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            policyVo.setPolicy(encodedPolicy);
            policyVo.setAccessId(aliOssProperties.getAccessKeyId());
            policyVo.setSignature(postSignature);
            policyVo.setDir(dir);
            policyVo.setHost(host);
            policyVo.setExpire(String.valueOf(expireEndTime / 1000));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Response.ok(policyVo);
    }

}
