package com.ningf.ningfojbackendjudgeservice.judge;


import com.ningf.ningfojbackendmodel.model.entity.QuestionSubmit;

public interface JudgeService {
    QuestionSubmit doJudge(long questionSubmitId);
}
