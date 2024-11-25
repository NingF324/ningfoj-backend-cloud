package com.ningf.ningfojbackendjudgeservice.judge.codeSandBox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.ningf.ningfojbackendcommon.common.ErrorCode;
import com.ningf.ningfojbackendcommon.exception.BusinessException;
import com.ningf.ningfojbackendjudgeservice.judge.codeSandBox.CodeSandBox;
import com.ningf.ningfojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.ningf.ningfojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * @description:实际调用接口的沙箱
 * @author: Lenovo
 * @time: 2024/11/11 下午9:38
 */
public class RemoteCodeSandBox implements CodeSandBox {
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = "secretKey";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("remote");
        String url ="http://192.168.227.132:9099/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER,AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        if (StringUtils.isBlank(responseStr)){
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode remoteSandBox error, message = "+ responseStr);
        }
        return JSONUtil.toBean(responseStr,ExecuteCodeResponse.class);
    }
}
