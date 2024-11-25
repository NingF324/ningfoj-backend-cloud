package com.ningf.ningfojbackendjudgeservice.judge.codeSandBox.impl;


import com.ningf.ningfojbackendjudgeservice.judge.codeSandBox.CodeSandBox;
import com.ningf.ningfojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.ningf.ningfojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * @description:第三方代码沙箱
 * @author: Lenovo
 * @time: 2024/11/11 下午9:38
 */
public class ThirdPartyCodeSandBox implements CodeSandBox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("thirdParty");
        return null;
    }
}
