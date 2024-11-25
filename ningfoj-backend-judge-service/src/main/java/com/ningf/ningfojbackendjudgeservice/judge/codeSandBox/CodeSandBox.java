package com.ningf.ningfojbackendjudgeservice.judge.codeSandBox;


import com.ningf.ningfojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.ningf.ningfojbackendmodel.model.codesandbox.ExecuteCodeResponse;

public interface CodeSandBox {

    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
