package com.ningf.ningfojbackendjudgeservice.judge.strategy;


import com.ningf.ningfojbackendmodel.model.codesandbox.JudgeInfo;

public interface JudgeStrategy {
    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
