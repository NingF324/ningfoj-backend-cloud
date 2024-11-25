package com.ningf.ningfojbackendjudgeservice.judge.strategy;


import com.ningf.ningfojbackendmodel.model.codesandbox.JudgeInfo;
import com.ningf.ningfojbackendmodel.model.dto.question.JudgeCase;
import com.ningf.ningfojbackendmodel.model.entity.Question;
import com.ningf.ningfojbackendmodel.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * @description: 用于定义所需传递的参数
 * @author: Lenovo
 * @time: 2024/11/12 上午9:34
 */
@Data
public class JudgeContext {
    private JudgeInfo judgeInfo;
    private List<String> inputList;
    private List<String> outputList;
    private Question question;
    private List<JudgeCase> judgeCases;
    private QuestionSubmit questionSubmit;
}
