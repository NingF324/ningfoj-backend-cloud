package com.ningf.ningfojbackendjudgeservice.judge.codeSandBox.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.ningf.ningfojbackendcommon.common.ErrorCode;
import com.ningf.ningfojbackendcommon.exception.BusinessException;
import com.ningf.ningfojbackendjudgeservice.judge.codeSandBox.CodeSandBox;
import com.ningf.ningfojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.ningf.ningfojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:实际调用接口的沙箱
 * @author: Lenovo
 * @time: 2024/11/11 下午9:38
 */
public class RemoteCodeSandBox implements CodeSandBox {
    private static final String ACCESS_KEY = "auth";

    private static final String SECRET_KEY = "secretKey";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("remote");
        String url ="http://192.168.227.132:9099/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute()
                .body();
        if (StringUtils.isBlank(responseStr)){
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode remoteSandBox error, message = "+ responseStr);
        }
        return JSONUtil.toBean(responseStr,ExecuteCodeResponse.class);
    }

    /**
     * 获取请求头的哈希映射
     * @param body 请求体内容
     * @return 包含请求头参数的哈希映射
     */
    private Map<String, String> getHeaderMap(String body) {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey", ACCESS_KEY);
        // 生成随机数(生成一个包含100个随机数字的字符串)
        hashMap.put("nonce", RandomUtil.randomNumbers(100));
        // 请求体内容
        hashMap.put("body", body);
        // 当前时间戳
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        // 生成签名
        hashMap.put("sign", genSign(body, SECRET_KEY));
        return hashMap;
    }

    /**
     * 生成签名
     * @param body 包含需要签名的参数的哈希映射
     * @param secretKey 密钥
     * @return 生成的签名字符串
     */
    public static String genSign(String body, String secretKey) {
        // 使用SHA256算法的Digester
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        // 构建签名内容，将哈希映射转换为字符串并拼接密钥
        String content = body + "." + secretKey;
        // 计算签名的摘要并返回摘要的十六进制表示形式
        return md5.digestHex(content);
    }
}

