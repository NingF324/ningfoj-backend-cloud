package com.ningf.ningfojbackendjudgeservice.judge;


import com.ningf.ningfojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.ningf.ningfojbackendjudgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.ningf.ningfojbackendjudgeservice.judge.strategy.JudgeContext;
import com.ningf.ningfojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.ningf.ningfojbackendmodel.model.codesandbox.JudgeInfo;
import com.ningf.ningfojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * @description: 判题管理
 * @author: Lenovo
 * @time: 2024/11/12 下午7:38
 */

@Service
public class JudgeManager {
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if("java".equals(language)) {
            judgeStrategy= new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
