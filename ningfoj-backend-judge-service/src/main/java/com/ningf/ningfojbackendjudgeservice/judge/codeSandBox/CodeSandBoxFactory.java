package com.ningf.ningfojbackendjudgeservice.judge.codeSandBox;


import com.ningf.ningfojbackendjudgeservice.judge.codeSandBox.impl.ExampleCodeSandBox;
import com.ningf.ningfojbackendjudgeservice.judge.codeSandBox.impl.RemoteCodeSandBox;
import com.ningf.ningfojbackendjudgeservice.judge.codeSandBox.impl.ThirdPartyCodeSandBox;

/**
 * @description:
 * @author: Lenovo
 * @time: 2024/11/11 下午9:55
 */
public class CodeSandBoxFactory {
    public static CodeSandBox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandBox();
            case "remote":
                return new RemoteCodeSandBox();
            case "thirdParty":
                return new ThirdPartyCodeSandBox();
            default:
                return new ExampleCodeSandBox();
        }
    }
}
