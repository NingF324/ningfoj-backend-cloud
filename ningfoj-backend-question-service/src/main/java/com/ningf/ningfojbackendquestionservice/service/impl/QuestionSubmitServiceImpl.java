package com.ningf.ningfojbackendquestionservice.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ningf.ningfojbackendcommon.common.ErrorCode;
import com.ningf.ningfojbackendcommon.constant.CommonConstant;
import com.ningf.ningfojbackendcommon.exception.BusinessException;
import com.ningf.ningfojbackendcommon.utils.SqlUtils;
import com.ningf.ningfojbackendmodel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.ningf.ningfojbackendmodel.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.ningf.ningfojbackendmodel.model.entity.Question;
import com.ningf.ningfojbackendmodel.model.entity.QuestionSubmit;
import com.ningf.ningfojbackendmodel.model.entity.User;
import com.ningf.ningfojbackendmodel.model.enums.QuestionSubmitLanguageEnum;
import com.ningf.ningfojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.ningf.ningfojbackendmodel.model.vo.QuestionSubmitVO;
import com.ningf.ningfojbackendquestionservice.mapper.QuestionSubmitMapper;
import com.ningf.ningfojbackendquestionservice.rabbitmq.MyMessageProducer;
import com.ningf.ningfojbackendquestionservice.service.QuestionService;
import com.ningf.ningfojbackendquestionservice.service.QuestionSubmitService;
import com.ningf.ningfojbackendserviceclient.service.JudgeFeignClient;
import com.ningf.ningfojbackendserviceclient.service.UserFeignClient;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Lenovo
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2024-10-14 09:37:22
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService {
    @Resource
    private QuestionService questionService;

    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    @Lazy
    private JudgeFeignClient judgeFeignClient;

    @Resource
    private MyMessageProducer myMessageProducer;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        long questionId = questionSubmitAddRequest.getQuestionId();

        //校验语言是否合法
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if(languageEnum==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"編程語言錯誤");
        }
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交题目
        long userId = loginUser.getId();
        // 每个用户提交题目
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(questionSubmitAddRequest.getLanguage());
        //todo 设置初始状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean result = this.save(questionSubmit);
        if(!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"提交插入失败");
        }
        Long questionSubmitId = questionSubmit.getId();
        //发送消息到消息队列
        myMessageProducer.sendMessage("code_exchange","my_routingKey",String.valueOf(questionSubmitId));
        return questionSubmitId;
    }

    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitSubmitQueryRequest == null) {
            return queryWrapper;
        }
        String language = questionSubmitSubmitQueryRequest.getLanguage();
        Integer status = questionSubmitSubmitQueryRequest.getStatus();
        Long questionId = questionSubmitSubmitQueryRequest.getQuestionId();
        Long userId = questionSubmitSubmitQueryRequest.getUserId();
        String sortField = questionSubmitSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitSubmitQueryRequest.getSortOrder();


        // 拼接查询条件
        queryWrapper.eq(StringUtils.isNotBlank(language), "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq(QuestionSubmitStatusEnum.getEnumByValue(status) != null,"status",status);
        queryWrapper.eq("isDelete",false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser) {
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        //脱敏：分页获取题目提交信息列表（仅管理员与答题者可见提交的代码，其余用户只能看到是否通过）
        long userId = loginUser.getId();
        if(userId != questionSubmit.getUserId() && !userFeignClient.isAdmin(loginUser)){
            questionSubmitVO.setCode(null);
        }

        return questionSubmitVO;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser) {
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionSubmitSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollUtil.isEmpty(questionSubmitList)) {
            return questionSubmitSubmitVOPage;
        }
        List<QuestionSubmitVO> questionSubmitVOList = questionSubmitList.stream().map(questionSubmit ->
                getQuestionSubmitVO(questionSubmit, loginUser)
        ).collect(Collectors.toList());
        questionSubmitSubmitVOPage.setRecords(questionSubmitVOList);
        return questionSubmitSubmitVOPage;
    }

}




