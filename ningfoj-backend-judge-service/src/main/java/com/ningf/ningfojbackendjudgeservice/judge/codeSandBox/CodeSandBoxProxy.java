package com.ningf.ningfojbackendjudgeservice.judge.codeSandBox;


import com.ningf.ningfojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.ningf.ningfojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: Lenovo
 * @time: 2024/11/12 上午8:17
 */
@Slf4j
public class CodeSandBoxProxy implements CodeSandBox{
    private final CodeSandBox codeSandBox;

    public CodeSandBoxProxy(CodeSandBox codeSandBox){
        this.codeSandBox=codeSandBox;
    }
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息{}", executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        log.info("代码沙箱响应信息{}", executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
