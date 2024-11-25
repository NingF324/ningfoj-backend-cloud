package com.ningf.ningfojbackendjudgeservice.controller;

import com.ningf.ningfojbackendjudgeservice.judge.JudgeService;
import com.ningf.ningfojbackendmodel.model.entity.QuestionSubmit;
import com.ningf.ningfojbackendserviceclient.service.JudgeFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description:
 * @author: Lenovo
 * @time: 2024/11/22 上午10:40
 */
@RestController
@RequestMapping("/inner")
public class JudgeInnerController implements JudgeFeignClient {
    @Resource
    private JudgeService judgeService;

    @Override
    @PostMapping("/do")
    public QuestionSubmit doJudge(long questionSubmitId) {
        return judgeService.doJudge(questionSubmitId);
    }
}
